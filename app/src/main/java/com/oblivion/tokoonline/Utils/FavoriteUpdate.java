package com.oblivion.tokoonline.Utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.oblivion.tokoonline.R;
import com.oblivion.tokoonline.model.ItemSell_model;
import com.oblivion.tokoonline.view.ItemViewDetail;

import java.util.EventListener;

public class FavoriteUpdate {

    private long maxid_post = 1;

    public void userUpdateFavoriteItem(final String idItem, final ImageView view, final Context context){

        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();


        final DatabaseReference getUserFavorite = FirebaseDatabase.getInstance().getReference("userItemFavorite").child(mUser.getUid());


        Query getLastCounter = getUserFavorite.orderByKey().limitToLast(1);

        getLastCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren())
                {
                    maxid_post = Long.parseLong(ds.getKey()) + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getUserFavorite.orderByChild("idUpload").equalTo(idItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    deleteFavorite(mUser.getUid(), idItem, view);
                }else {

                    getUserFavorite.child(String.valueOf(maxid_post)).child("idUpload").setValue(idItem);
                    updateFavorite(idItem);

                    view.setImageResource(R.drawable.ic_favorite_purple);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setUserFavoriteItem(String userUid, final String idItem, final ImageView view){
        final DatabaseReference getUserFavorite = FirebaseDatabase.getInstance().getReference("userItemFavorite").child(userUid);


        getUserFavorite.orderByChild("idUpload").equalTo(idItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    view.setImageResource(R.drawable.ic_favorite_purple);
                }else {
                    view.setImageResource(R.drawable.ic_favorite_border);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void updateFavorite(final String idItem){

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");

        reference.orderByKey().equalTo(idItem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        ItemSell_model itemSellModel = ds.getValue(ItemSell_model.class);

                        int favorite = Integer.parseInt(itemSellModel.getFavorite()) + 1;

                        reference.child(idItem).child("favorite").setValue(String.valueOf(favorite));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void deleteFavorite(final String userUid, final String iditem, final ImageView view){

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("itemSell");

        reference.orderByChild("idUpload").equalTo(iditem).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {

                    for (DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        ItemSell_model itemSellModel = ds.getValue(ItemSell_model.class);

                        int favorite = Integer.parseInt(itemSellModel.getFavorite()) - 1;

                        reference.child(iditem).child("favorite").setValue(String.valueOf(favorite));
                    }

                    deleteUserFavorite(userUid, iditem, view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void deleteUserFavorite(String userUid, String idItem, final ImageView view){
        final DatabaseReference getUserFavorite = FirebaseDatabase.getInstance()
                .getReference("userItemFavorite");

        Query deleteUserFav = getUserFavorite.child(userUid).orderByChild("idUpload").equalTo(idItem);

        deleteUserFav.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsDelete : dataSnapshot.getChildren()){

                    dsDelete.getRef().removeValue();

                }

                view.setImageResource(R.drawable.ic_favorite_border);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
