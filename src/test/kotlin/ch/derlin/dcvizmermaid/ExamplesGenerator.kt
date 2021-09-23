package ch.derlin.dcvizmermaid

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.isSuccess
import ch.derlin.dcvizmermaid.graph.GraphTheme
import ch.derlin.dcvizmermaid.graph.MermaidOutput
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Path

class ExamplesGenerator {

    @Test
    fun `generate examples`() {
        assertAll {
            File("examples").walkTopDown()
                .filter { it.isFile && it.extension == "yaml" }
                .forEach { assertThat { processYaml(it) }.isSuccess() }
        }
    }

    private fun processYaml(file: File) {

        val text = file.readText()
        val options = text.lines().first().substringAfter("#").takeIf { it.trim().all { c -> c.lowercase() in "pvc" } } ?: ""

        GraphTheme.values().forEach { theme ->
            val themeName = theme.name.lowercase()
            val graph = generateMermaidGraph(
                text,
                theme = theme,
                withPorts = 'p' in options,
                withVolumes = 'v' in options,
                withClasses = 'c' in options,
            )
            MermaidOutput.MARKDOWN
                .process(graph, Path.of(file.absolutePath.replace(".yaml", "-$themeName.md")), withBackground = true)
            MermaidOutput.PNG
                .process(graph, Path.of(file.absolutePath.replace(".yaml", "-$themeName.png")), withBackground = true)
        }
    }
}