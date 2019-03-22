package krypto

import javax.swing.*
import java.awt.*
import javax.swing.SwingUtilities.invokeLater
import javax.swing.border.TitledBorder

fun run(f: JFrame, width: Int, height: Int) {
    invokeLater{
        f.title = f::class.java.simpleName
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.setSize(width, height)
        f.isVisible = true
    }
}

class EncryptWindow : JFrame() {
    private val decoded = JTextField(20)
    private val coded = JTextField(20)
    private val keyWord = JTextField(20)
    private val code = JButton("ENCRYPT")
    private val decode = JButton("DECRYPT")
    private lateinit var matrix: SquareKt

    init {
        code.addActionListener {
            coded.text = matrix.code(decoded.text)
            decoded.text = ""
        }
        decode.addActionListener {
            decoded.text = matrix.decode(coded.text)
            coded.text = ""
        }
        keyWord.addActionListener {
            matrix = SquareKt(keyWord.text)
        }
        val keyPanel = JPanel()
        val codePanel = JPanel()
        val decodePanel = JPanel()
        keyPanel.add(keyWord)
        keyPanel.border = TitledBorder("keyword")
        codePanel.add(code)
        codePanel.add(decoded)
        decodePanel.add(decode)
        decodePanel.add(coded)
        layout = GridLayout(3, 1)
        add(keyPanel); add(codePanel); add(decodePanel)
    }

    companion object {
        @Suppress("UnusedMainParameter")
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            run(EncryptWindow(), 300, 200)
        }
    }
}