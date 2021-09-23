package ch.derlin.dcvizmermaid

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isSuccess
import ch.derlin.dcvizmermaid.graph.GraphTheme
import ch.derlin.dcvizmermaid.graph.MermaidOutput
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Path
import kotlin.io.path.bufferedWriter

class ExamplesGenerator {

    companion object {
        private const val outputPathImages = "jekyll/assets/generated"
        private const val outputPathText = "jekyll/_includes/generated"

        @BeforeAll
        @JvmStatic
        fun cleanup() {
            listOf(outputPathImages, outputPathText).map { File(it) }.forEach {
                it.deleteRecursively()
                it.mkdir()
            }
        }
    }

    @Test
    fun `generate examples`() {
        assertAll {
            File("examples").walkTopDown()
                .filter { it.isFile && it.extension == "yaml" }
                .forEach { assertThat { processYaml(it) }.isSuccess() }
        }
    }

    private fun processYaml(file: File) {

        file.copyTo(File("$outputPathText/${file.name}"))

        val text = file.readText()
        val options = text.lines().first().substringAfter("#").takeIf { it.trim().all { c -> c.lowercase() in "pvc" } } ?: ""

        GraphTheme.values().forEach { theme ->
            val basename = "${file.nameWithoutExtension}-${theme.name.lowercase()}"
            val graph = generateMermaidGraph(
                text,
                theme = theme,
                withPorts = 'p' in options,
                withVolumes = 'v' in options,
                withClasses = 'c' in options,
            )

            MermaidOutput.MARKDOWN.process(graph, Path.of("$outputPathText/$basename.md"), withBackground = false)
            MermaidOutput.SVG.process(graph, Path.of("$outputPathImages/$basename.svg"), withBackground = true)
            MermaidOutput.PNG.process(graph, Path.of("$outputPathImages/$basename.png"), withBackground = true)
        }
    }

    private fun File.outputPathForImages(ext: String) =
        Path.of("jekyll/assets/generated/${nameWithoutExtension}$ext")


}