package com.example.financekeeperjava.presentation.main.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.financekeeperjava.R;
import com.example.financekeeperjava.adapters.ProfileAdapter;
import com.example.financekeeperjava.data.API;
import com.example.financekeeperjava.data.ApiInterface;
import com.example.financekeeperjava.data.func.LoginFunctions;
import com.example.financekeeperjava.data.model.User;
import com.example.financekeeperjava.presentation.main.profile.categories.CategoryActivity;
import com.example.financekeeperjava.presentation.main.profile.wallets.WalletActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;


public class ProfileFragment extends Fragment {

    ListView listView;

    ImageView avatar;

    private Context mContext; // Переменная для хранения контекста активности

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context; // Сохраняем контекст активности
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avatar = view.findViewById(R.id.avatarImageView);
        ApiInterface apiInterface = API.buildRequest().create(ApiInterface.class);
        String imageUrl = LoginFunctions.getLoggedUser().getAvatar();
        String noAvatarUrl = "https://w7.pngwing.com/pngs/697/832/png-transparent-the-law-office-of-steve-slough-business-medicine-management-company-business-angle-company-service.png";
        if (Objects.equals(imageUrl, "none") || Objects.equals(imageUrl, "") || Objects.equals(imageUrl, "NONE")) {
            Picasso.with(mContext).load(noAvatarUrl).into(avatar);
        }
        else {
            Picasso.with(mContext).load(imageUrl).into(avatar);
        }


        ArrayList<ProfileItem> profileItems = new ArrayList<>();

        profileItems.add(new ProfileItem(R.drawable.person_24, "Управление аккаунтом", "Настройки вашего аккаунта"));
        profileItems.add(new ProfileItem(R.drawable.clear_24, "Очистка", "Очистка данных приложения"));
        profileItems.add(new ProfileItem(R.drawable.baseline_category_24, "Категории", "Управление категориями"));
        profileItems.add(new ProfileItem(R.drawable.wallet_24, "Кошельки", "Действия с кошельками"));

        // Создайте адаптер и установите его в ListView
        ProfileAdapter profileAdapter = new ProfileAdapter(mContext, profileItems);
        ListView profileListView = view.findViewById(R.id.profileListView);
        profileListView.setAdapter(profileAdapter);

        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProfileItem selectedItem = profileItems.get(position);
                if (selectedItem.getTitle().equals("Управление аккаунтом")) {
                    Intent accountIntent = new Intent(mContext, AccountEditActivity.class);
                    startActivity(accountIntent);
                } else if (selectedItem.getTitle().equals("Настройки")) {
                    Intent settingsIntent = new Intent(mContext, SettingsActivity.class);
                    startActivity(settingsIntent);
                } else if (selectedItem.getTitle().equals("Очистка")) {
                    Intent clearIntent = new Intent(mContext, ClearingActivity.class);
                    startActivity(clearIntent);
                } else if (selectedItem.getTitle().equals("Категории")) {
                    Intent templateIntent = new Intent(mContext, CategoryActivity.class);
                    startActivity(templateIntent);
                } else if (selectedItem.getTitle().equals("Кошельки")) {
                    Intent walletIntent = new Intent(mContext, WalletActivity.class);
                    startActivity(walletIntent);
                }
            }
        });
    }
}