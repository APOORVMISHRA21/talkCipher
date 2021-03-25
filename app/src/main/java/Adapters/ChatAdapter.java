package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkcipher.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import Model.Messages;

public class ChatAdapter extends RecyclerView.Adapter {

    public ChatAdapter(ArrayList<Messages> messages, Context context) {
        Messages = messages;
        this.context = context;
    }


    ArrayList<Model.Messages> Messages;
    Context context;

    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent, false);
            return new senderViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent, false );
            return new recieverViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messages message = Messages.get(position);
        if(holder.getClass() == senderViewHolder.class)
        {
            ((senderViewHolder) holder).senderMessage.setText(message.getMessage());
        }
        else
        {
            ((recieverViewHolder) holder).recieverMessage.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return Messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(Messages.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIEVER_VIEW_TYPE;
        }
    }

    public class senderViewHolder extends RecyclerView.ViewHolder{
        TextView senderMessage, senderTime;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }

    public class recieverViewHolder extends RecyclerView.ViewHolder{
        TextView recieverMessage, recieverTime;
        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMessage = itemView.findViewById(R.id.recieverText);
            recieverTime = itemView.findViewById(R.id.recieverTime);
        }
    }
}
