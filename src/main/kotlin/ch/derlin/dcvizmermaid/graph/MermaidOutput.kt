package ch.derlin.dcvizmermaid.graph

import java.io.File
import java.net.URL
import java.nio.file.Path
import java.util.*

enum class MermaidOutput {
    TEXT, MARKDOWN, EDITOR, PREVIEW, PNG, SVG;

    fun process(mermaidGraph: MermaidGraph, outputFile: Path? = null, withBackground: Boolean = false) {
        val text = mermaidGraph.build(withBackground)
        fun asBase64() = text.toBase64(mermaidGraph.theme)
        when (this) {
            TEXT -> outputFile.print(text)
            MARKDOWN -> outputFile.print("```mermaid\n$text```")
            EDITOR -> println("https://mermaid-js.github.io/mermaid-live-editor/edit#${asBase64()}")
            PREVIEW -> println("https://mermaid-js.github.io/mermaid-live-editor/view/#${asBase64()}")
            PNG -> println("Saved image to ${downloadImage("https://mermaid.ink/img/${asBase64()}", outputFile)}")
            SVG -> println("Saved image to ${downloadImage("https://mermaid.ink/svg/${asBase64()}", outputFile, ext = "svg")}")
        }
    }

    private fun Path?.print(content: String) {
        this?.toFile()?.let {
            it.writeText(content); println("Written graph to ${it.absolutePath}")
        } ?: println(content)
    }

    private fun String.toBase64(theme: GraphTheme = GraphTheme.DEFAULT): String {
        val escapedCode = replace("\"", "\\\"").replace("\n", "\\n")
        val data = "{\"code\":\"$escapedCode\"," +
                "\"mermaid\": {\"theme\": \"${theme.name.lowercase()}\"},\"updateEditor\":true,\"autoSync\":true,\"updateDiagram\":true}"
        return Base64.getEncoder().encodeToString(data.toByteArray()).trimEnd('=')
    }

    private fun downloadImage(url: String, outputPath: Path? = null, ext: String = "png"): String {
        val outputFile = outputPath?.toFile() ?: File("image.$ext")
        URL(url).openStream().transferTo(outputFile.outputStream())
        return outputFile.absolutePath
    }
}