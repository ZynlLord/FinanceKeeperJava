package com.example.financekeeperjava.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.data.model.Wallet;
import com.example.financekeeperjava.presentation.main.profile.wallets.WalletInfoActivity;

import java.util.List;

public class WalletListAdapter extends ArrayAdapter<Wallet> {

    private Context context;
    private List<Wallet> walletList;

    public WalletListAdapter(Context context, List<Wallet> walletList) {
        super(context, R.layout.wallet_item, walletList);
        this.context = context;
        this.walletList = walletList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItemView = inflater.inflate(R.layout.wallet_item, null, true);

        TextView walletNameTextView = listItemView.findViewById(R.id.wallet_name_text_view);
        TextView walletBalanceTextView = listItemView.findViewById(R.id.wallet_balance_text_view);
        CardView walletCardView = listItemView.findViewById(R.id.card_wallet);

        Wallet wallet = walletList.get(position);

        walletNameTextView.setText(wallet.getName());
        walletBalanceTextView.setText(String.valueOf(wallet.getBalance()));

        walletCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WalletInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("walletName", wallet.getName());
                intent.putExtra("walletCurrency", wallet.getCurrency());
                intent.putExtra("walletBalance", wallet.getBalance());
                intent.putExtra("walletUserId", wallet.getIdUser());
                intent.putExtra("walletCreatedAt", wallet.getCreatedAt());
                intent.putExtra("walletIcon", wallet.getIcon());
                intent.putExtra("walletId", wallet.getIdWallet());
                getContext().startActivity(intent);
            }
        });


        return listItemView;
    }
}
