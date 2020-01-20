package amsi.dei.estg.ipleiria.infortec_android;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompraFragment extends DialogFragment implements View.OnClickListener {

    private Button btnNao;
    private Button btnSim;

    public CompraFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compra, container, false);

        System.out.println("---> Nao a funcionar ");
        btnSim =  (Button) view.findViewById(R.id.btnBuy);

        btnSim.setOnClickListener(this);


        setCancelable(true);
        return view;
    }

    @Override
    public void onClick(View v) {
        System.out.println("---> Esta a funcionar " + v);
        if (v.getId() == R.id.btnBuy)
            System.out.println("---> Esta a funcionar ");
        if (v.getId() == R.id.btnCancel)
            System.out.println("---> Esta a funcionar ");
        dismiss();
    }
}
