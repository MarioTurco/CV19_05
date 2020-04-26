package com.example.provacv;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListaStruttureRecyclerViewAdapter extends RecyclerView.Adapter<ListaStruttureRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ListaStruttureRecyclerV";

    private Context mContext;
    //qui vanno le robe prese dall'activity che a scua volta dovrebbe prenderle dal dao/locale
    private ArrayList<String> listaNomi = new ArrayList<>();
    private ArrayList<String> listaCittà = new ArrayList<>();
    private ArrayList<String> listaDescrizioni = new ArrayList<>();
    private ArrayList<String> listaCategorie = new ArrayList<>();

    public ListaStruttureRecyclerViewAdapter(Context mContext, ArrayList<String> nomi, ArrayList<String> città, ArrayList<String> descrizioni, ArrayList<String> categoria) {
        this.mContext = mContext;
        this.listaNomi = nomi;
        this.listaCittà = città;
        this.listaDescrizioni = descrizioni;
        this.listaCategorie = categoria;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_strutture_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");

        //qui dobbiamo caricare l'immagine
        //...

        //qui settiamo i vari campi di testo
        holder.nome.setText(listaNomi.get(position));
        holder.categoria.setText(listaCategorie.get(position));
        holder.città.setText(listaCittà.get(position));
        holder.descrizione.setText(listaDescrizioni.get(position));

        //listener che ci fa aprire la pagina della struttura
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Cliccato", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaNomi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView immagine;
        TextView nome, città, descrizione, categoria;
        //RatingBar ratingBar;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachItemsByID(itemView);
        }

        private void attachItemsByID(View itemView) {
            immagine = itemView.findViewById(R.id.listItem_immagineStruttura);
            nome = itemView.findViewById(R.id.listItem_NomeStruttura);
            città = itemView.findViewById(R.id.listItem_cittàStruttura);
            descrizione = itemView.findViewById(R.id.listItem_descrizioneStruttura);
            categoria = itemView.findViewById(R.id.listItem_categoriaStruttura);
           // ratingBar = itemView.findViewById(R.id.listItem_ratingStruttura);
            relativeLayout = itemView.findViewById(R.id.listItem_layout);
        }
    }
}
