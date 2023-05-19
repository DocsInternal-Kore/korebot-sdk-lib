package com.kore.korebot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import kore.botssdk.bot.BotClient;
import kore.botssdk.net.SDKConfiguration;
import kore.botssdk.websocket.SocketConnectionListener;

public class MainActivity extends AppCompatActivity implements SocketConnectionListener {

    private EditText edtText;
    private TextView tvSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtText = findViewById(R.id.edtText);
        tvSendMessage = findViewById(R.id.tvSendMessage);

        //Initiating BotClient
        BotClient botClient = new BotClient(this);

        //Local library to generate JWT token can be replaced as per requirement
        String jwt = botClient.generateJWT(SDKConfiguration.Client.identity,SDKConfiguration.Client.client_secret,SDKConfiguration.Client.client_id,SDKConfiguration.Server.IS_ANONYMOUS_USER);

        //Initiating bot connection once connected callbacks will be fired on respective actions
        botClient.connectAsAnonymousUser(jwt,SDKConfiguration.Client.bot_name,SDKConfiguration.Client.bot_id, MainActivity.this);

        tvSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Send message to bot with help of botclient
                if(edtText.getText().toString().length() > 0)
                    botClient.sendMessage(edtText.getText().toString());
            }
        });

    }

    /**
     * Fired when the WebSockets connection has been established.
     * After this happened, messages may be sent.
     */
    @Override
    public void onOpen(boolean isReconnection) {
        Log.e("Connection", isReconnection+"");
    }

    /**
     * Fired when the WebSockets connection has deceased (or could
     * not established in the first place).
     *
     * @param code   Close code.
     * @param reason Close reason (human-readable).
     */
    @Override
    public void onClose(int code, String reason) {
        Log.e("Connection Close", reason+""+code);
    }

    /**
     * Fired when a text message has been received (and text
     * messages are not set to be received raw).
     *
     * @param payload Text message payload or null (empty payload).
     */
    @Override
    public void onTextMessage(String payload) {
        Log.e("onTextMessage payload", payload);
    }

    /**
     * Fired when a text message has been received (and text
     * messages are set to be received raw).
     *
     * @param payload Text message payload as raw UTF-8 or null (empty payload).
     */
    @Override
    public void onRawTextMessage(byte[] payload) {
        Log.e("RawTextMessage payload", payload+"");
    }

    /**
     * Fired when a binary message has been received.
     *
     * @param payload Binar message payload or null (empty payload).
     */
    @Override
    public void onBinaryMessage(byte[] payload) {
        Log.e("BinaryMessage payload", payload+"");
    }

    //When jwt token expires we can refresh token
    @Override
    public void refreshJwtToken() {

    }

    /**
     * Callback for when user Connected
     *//*
    void onConnected(String message);

	*//**
     * Callback for when user Disconnected
     *//*
	void onDisconnected(String reason);*/
}