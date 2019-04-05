package gui.tronado

import javafx.beans.property.SimpleStringProperty
import krypto.SquareKt
import tornadofx.*

class EncryptApp : App(EncryptView::class)

class EncryptView : View(){
    var encrypted = SimpleStringProperty()
    var decrypted = SimpleStringProperty()
    override val root = vbox {
        button("new keyword"){
            action { openInternalWindow<KeywordView>() }
        }
        textfield(decrypted)
        button("Encrypt"){
            action {
                if(Controller.noSquare()) { openInternalWindow<KeywordView>() }
                else encrypted.value = Controller.code(decrypted.value)
            }
        }
        textfield(encrypted)
        button("Decrypt"){
            action {
                if(Controller.noSquare()) { openInternalWindow<KeywordView>() }
                else decrypted.value = Controller.decode(encrypted.value)
            }
        }
    }
}

class KeywordView: View(){
    private var keyword = SimpleStringProperty()
    override val root = vbox {
        label("enter keyword")
        textfield(keyword)
        button("Create"){
            action {
                Controller.createSquare(keyword.value)
                close()
            }
        }
    }
}

object Controller{
    private var square: SquareKt? =null
    public fun createSquare(keyword: String) { square = SquareKt(keyword) }
    public fun noSquare() = square==null
    public fun decode(coded: String) = square?.decode(coded)
    public fun code(decoded: String) = square?.code(decoded)
}