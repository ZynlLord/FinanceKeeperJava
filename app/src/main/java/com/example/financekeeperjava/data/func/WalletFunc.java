package com.example.financekeeperjava.data.func;

import android.util.Log;
import com.example.financekeeperjava.data.model.Wallet;
import java.util.ArrayList;
import io.paperdb.Paper;

public class WalletFunc {
    public static void saveWallet(Wallet wallet) {
        Paper.book("Wallet").write("wal", wallet);
    }

    public static boolean isActiveWalletExists() {
        Wallet wallet = Paper.book("Wallet").read("wal");
        if (wallet != null) {
            Log.d("Wallets", "Exists:" + wallet.getName());
            return true;
        } else {
            Log.d("Wallets", "No wallet exists!");
            return false;
        }
    }

    public static Wallet getActiveWallet() {
        return Paper.book("Wallet").read("wal");
    }

    public static void setNoActiveWallet() {
        Paper.book("Wallet").delete("wal");
        Log.d("Wallets", "wallet is now have no active");
    }

    public static Wallet findWalletByID(int id, ArrayList<Wallet> wallets) {
        for (Wallet wallet : wallets) {
            if (wallet.getIdWallet() == id) {
                return wallet;
            }
        }
        return null;
    }
}
