package au.cassa.paxton.slashcmd.impl

import au.cassa.paxton.Paxton.log
import au.cassa.paxton.config.impl.SettingsCfg
import au.cassa.paxton.slashcmd.SlashCmd
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.spongepowered.configurate.CommentedConfigurationNode
import java.time.Instant

/**
 * Posts a user-provided message template in the channel the command is ran in.
 */
object SendTemplateSlashCmd : SlashCmd(
    cmdData = Commands
        .slash("send-template", "Send message template")
        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.MANAGE_CHANNEL))
        .addOption(OptionType.STRING, "id", "Message template ID", true, true)
        // tell me about it, discord should have varargs support, lol.
        .addOption(OptionType.STRING, "placeholder1", "Placeholder #1", false, false)
        .addOption(OptionType.STRING, "placeholder2", "Placeholder #2", false, false)
        .addOption(OptionType.STRING, "placeholder3", "Placeholder #3", false, false)
        .addOption(OptionType.STRING, "placeholder4", "Placeholder #4", false, false)
        .addOption(OptionType.STRING, "placeholder5", "Placeholder #5", false, false)
        .addOption(OptionType.STRING, "placeholder6", "Placeholder #6", false, false)
        .addOption(OptionType.STRING, "placeholder7", "Placeholder #7", false, false)
        .addOption(OptionType.STRING, "placeholder8", "Placeholder #8", false, false)
        .addOption(OptionType.STRING, "placeholder9", "Placeholder #9", false, false)
        .addOption(OptionType.STRING, "placeholder10", "Placeholder #10", false, false)
) {

    const val DEFAULT_VAL_INLINE_FIELDS = false
    const val DEFAULT_VAL_CHECKED_FIELDS = false

    override fun onInteraction(
        event: SlashCommandInteractionEvent
    ) {
        val templateId = event.getOption("id")!!.asString

        val placeholders: Map<Int, String> = let {
            val l = mutableMapOf<Int, String>()
            for(i in 1..10) {
                val opt = event.getOption("placeholder$i") ?: continue
                l[i] = opt.asString
            }
            return@let l
        }

        val postTemplateNodes = SettingsCfg
            .rootNode
            .node("post-templates")
            .childrenList()

        val postTemplateNode = postTemplateNodes.find { node ->
            node.node("id").string!!.equals(templateId, ignoreCase = true)
        }

        if(postTemplateNode == null) {
            event
                .reply("There is no post template defined with ID '${templateId}'.")
                .setEphemeral(true)
                .queue()
            return
        }

        val messages = mutableListOf<MessageEmbed>()

        try {
            for(messageNode in postTemplateNode.node("messages").childrenList()) {
                messages.add(parseMessageNode(messageNode, placeholders))
            }
        } catch(ex: Exception) {
            log.severe("Unable to print message template ${templateId}:")
            ex.printStackTrace()

            event
                .reply("Unable to send message: ${ex.message}; see logs for further details.")
                .setEphemeral(true)
                .queue()

            return
        }

        event.channel.sendMessageEmbeds(messages).queue {
            event.reply("Posts sent.").setEphemeral(true).queue()
        }
    }

    override fun onAutoComplete(event: CommandAutoCompleteInteractionEvent) {
        when (event.focusedOption.name) {
            "id" -> {
                val availableIds: Collection<String> = SettingsCfg
                    .rootNode
                    .node("post-templates")
                    .childrenList()
                    .map { it.node("id").string!! }

                event.replyChoiceStrings(availableIds.filter { it.contains(event.focusedOption.value) }).queue()
            }

            else -> {
                // intentionally blank: no suggestions available
            }
        }
    }

    fun parseMessageNode(
        node: CommentedConfigurationNode,
        placeholders: Map<Int, String>
    ): MessageEmbed {
        fun fmtPlaceholders(inStr: String?): String? {
            if(inStr.isNullOrBlank() || placeholders.isEmpty())
                return inStr

            var str: String = inStr

            for(i in placeholders.keys) {
                str = str.replace("{Placeholder.${i}}", placeholders[i]!!)
            }

            return str
        }

        val builder = EmbedBuilder()

        builder.setTitle(fmtPlaceholders(node.node("title").string))
        builder.setDescription(fmtPlaceholders(node.node("description").string))
        builder.setUrl(fmtPlaceholders(node.node("url").string))
        builder.setThumbnail(fmtPlaceholders(node.node("thumbnail").string))
        builder.setImage(fmtPlaceholders(node.node("img").string))
        builder.setAuthor(
            fmtPlaceholders(node.node("author", "name").string),
            fmtPlaceholders(node.node("author", "url").string),
            fmtPlaceholders(node.node("author", "icon-url").string),
        )
        builder.setFooter(
            fmtPlaceholders(node.node("footer", "text").string),
            fmtPlaceholders(node.node("footer", "icon-url").string),
        )

        if(node.hasChild("timestamp")) {
            // note: consider adding placeholder support here
            builder.setTimestamp(Instant.ofEpochMilli(node.node("timestamp").long))
        }

        if(node.hasChild("color")) {
            // note: consider adding placeholder support here
            builder.setColor(node.node("color").int)
        }

        for(footerNode in node.node("fields").childrenList()) {
            builder.addField(MessageEmbed.Field(
                fmtPlaceholders(footerNode.node("name").string!!),
                fmtPlaceholders(footerNode.node("value").string!!),
                footerNode.node("inline").getBoolean(DEFAULT_VAL_INLINE_FIELDS),
                // note: consider adding placeholder support in the above and below lines.
                footerNode.node("checked").getBoolean(DEFAULT_VAL_CHECKED_FIELDS),
            ))
        }

        return builder.build()
    }

}