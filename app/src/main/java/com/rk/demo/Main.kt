package com.rk.demo

import android.content.res.Resources
import androidx.annotation.Keep
import com.rk.extension.ExtensionAPI
import com.rk.extension.ExtensionContext
import com.rk.extension.filetype.FileType
import com.rk.extension.filetype.FileTypeManager
import com.rk.extension.grammar.GrammarRegistry
import com.rk.extension.provider.AssetsFileResolver
import com.rk.extension.provider.FileProviderRegistry
import com.rk.extension.ui.Icon

class CiscoLanguage(resources: Resources) : FileType {

    override val extensions = listOf("ios", "cisco")

    override val textmateScope = "text.cisco"

    override val icon =
        Icon.ExternalResourceIcon(R.drawable.ic_language, resources)

    override val name = "cisco"

    override val title = "Cisco IOS"
}

@Keep
@Suppress("unused")
class Main(context: ExtensionContext) : ExtensionAPI(context) {

    private var fileResolver: AssetsFileResolver? = null

    private var ciscoLanguage: CiscoLanguage? = null

    override fun onLoad() {

        fileResolver = AssetsFileResolver(context.assets)

        FileProviderRegistry.getInstance()
            .addFileProvider(fileResolver!!)

        GrammarRegistry.getInstance()
            .loadGrammars("languages.json")

        ciscoLanguage = CiscoLanguage(context.resources)

        FileTypeManager.register(ciscoLanguage!!)
    }

    override fun onDispose() {

        fileResolver?.let {
            FileProviderRegistry.getInstance()
                .removeFileProvider(it)
        }

        ciscoLanguage?.let {
            FileTypeManager.unregister(it)
        }
    }
}
