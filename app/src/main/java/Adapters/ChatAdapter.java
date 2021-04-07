package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talkcipher.R;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
            Timestamp timestamp = new Timestamp(message.getTimeStamp());
            Date date = new Date(timestamp.getTime());


            SimpleDateFormat dateFormat = new SimpleDateFormat("KK:mm a");
            String mssgTime = dateFormat.format(date).toString();

            ((senderViewHolder) holder).senderTime1.setText(mssgTime);
        }
        else
        {
            ((recieverViewHolder) holder).recieverMessage.setText(message.getMessage());

            Timestamp timestamp = new Timestamp(message.getTimeStamp());
            Date date = new Date(timestamp.getTime());


            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");
            String mssgTime = dateFormat.format(date).toString();

            ((recieverViewHolder) holder).recieverTime.setText(mssgTime);

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
        TextView senderMessage, senderTime1;
        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime1 = itemView.findViewById(R.id.senderTime);
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
