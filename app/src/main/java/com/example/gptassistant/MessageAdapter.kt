// Добавьте в начало файла:
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    private val messages = mutableListOf<UserMessage>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.messageText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.textView.text = message.text
        holder.itemView.setBackgroundColor(
            if (message.isFromUser) Color.LTGRAY else Color.WHITE
        )
    }

    override fun getItemCount() = messages.size

    fun addMessage(message: UserMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }
}