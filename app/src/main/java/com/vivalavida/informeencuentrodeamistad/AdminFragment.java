package com.vivalavida.informeencuentrodeamistad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vivalavida.informeencuentrodeamistad.Adapter.ClearClicklistener;
import com.vivalavida.informeencuentrodeamistad.Adapter.TitleClickListener;
import com.vivalavida.informeencuentrodeamistad.Adapter.usersAdapter;
import com.vivalavida.informeencuentrodeamistad.Model.userModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminFragment extends Fragment implements TitleClickListener, ClearClicklistener {
    DatabaseReference mRef;
    RecyclerView rvUsers;
    List<userModel>userList;
    ArrayList<String>keylist;
    usersAdapter usersAdapter;
    String key;
    View view;
    ConstraintLayout layoutAntes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_layout, container, false);

        mRef = FirebaseDatabase.getInstance().getReference();
        rvUsers = view.findViewById(R.id.rv_users);

        layoutAntes = view.findViewById(R.id.antes);

        getUsers();
        return view;
    }

    private void getUsers(){
        //FirebaseAuth.getInstance().getCurrentUser().getUid()
        userList = new ArrayList<>();
        keylist = new ArrayList<>();//parent child
        mRef.child("encuentrodeamistad").child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    key = ds.getKey();//parent child
                    userModel um = ds.getValue(userModel.class);
                    userList.add(um);
                    keylist.add(key);//parent child
                }
                layoutAntes.setVisibility(View.GONE);
                setRvUsers();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setRvUsers(){
        usersAdapter = new usersAdapter(getActivity(),userList,this,this,"");
        rvUsers.setAdapter(usersAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvUsers.setLayoutManager(lm);
        usersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClearClick(userModel userModel, int adapterPosition, ConstraintLayout clear, String correo) {
        final String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        new AlertDialog.Builder(getActivity())
                .setTitle("Eliminar usuario")
                .setMessage("Esta seguro de eliminar todos los datos de este usuario?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (userModel.getCorreo().equals(user)){
                            Toast.makeText(getActivity(), "No puede elimiar los datos del usuario actual", Toast.LENGTH_LONG).show();
                        }else{
                            deleteDatauser(keylist.get(adapterPosition));
                        }
                    }
                })
                .setNegativeButton("No",null)
                .show();

    }

    private void deleteDatauser(String key) {
        mRef.child("encuentrodeamistad").child("usuarios").child(key).setValue(null);
        getActivity().recreate();
        Toast.makeText(getActivity(), "Datos eliminados correctamente", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTitleClick(userModel userModel, int adapterPosition, ConstraintLayout rootTitle, TextView title) {
        //Toast.makeText(getActivity(), ""+keylist.get(adapterPosition), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getActivity(),GetUserDateActivity.class);
        i.putExtra("child",keylist.get(adapterPosition));
        i.putExtra("name",userModel.getNombre());
        i.putExtra("provider","adminFragment");
        i.putExtra("datechild","");////GetUserDetailsActivity;
        startActivity(i);
    }
}
