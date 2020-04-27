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

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import model.Struttura;

public class ListaStruttureRecyclerViewAdapter extends RecyclerView.Adapter<ListaStruttureRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ListaStruttureRecyclerV";

    private Context mContext;
    //qui vanno le robe prese dall'activity che a sua volta dovrebbe prenderle dal dao/locale
    private ArrayList<Struttura> listaStruttura;

    public ListaStruttureRecyclerViewAdapter(Context mContext, ArrayList<Struttura> listaStrutture) {
        this.mContext = mContext;
        this.listaStruttura = listaStrutture;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_strutture_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        Struttura struttura = listaStruttura.get(position);

        //TODO scarica immagne dal db
        //qui dobbiamo caricare l'immagine
        //...

        //qui settiamo i vari campi di testo
        holder.nome.setText(struttura.getNome());
        holder.categoria.setText(struttura.getCategoria());
        holder.città.setText(struttura.getCittà());
        holder.descrizione.setText(struttura.getDescrizione());

        //listener che ci fa aprire la pagina della struttura TODO: implementare listener che apre la pagina della struttura
        holder.ViewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Cliccato" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaStruttura.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView immagine;
        TextView nome, città, descrizione, categoria;
        //RatingBar ratingBar;
        MaterialCardView ViewLayout;
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
            ViewLayout = itemView.findViewById(R.id.listItem_layout);
        }
    }
}
