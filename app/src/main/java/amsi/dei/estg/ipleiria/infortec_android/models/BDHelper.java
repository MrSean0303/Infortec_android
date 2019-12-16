package amsi.dei.estg.ipleiria.infortec_android.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {
    private final SQLiteDatabase database;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "InfortecDB";
    private static final String TABLE_PRODUTO_NAME = "Produto";

    private static final String ID_PRODUTO = "id";
    private static final String NOME_PRODUTO = "nome";
    private static final String DESCRICAO_PRODUTO = "descricao";
    private static final String DESCRICAO_GERAL_PRODUTO = "descricaoGeral";
    private static final String FOTO_PRODUTO = "fotoProduto";
    private static final String QUANTSTOCK_PRODUTO = "quantStock";
    private static final String PONTOS_PRODUTO = "pontos";
    private static final String SUBCATEGORIA_ID_PRODUTO = "subcategoria_id";
    private static final String PRECO_PRODUTO = "preco";
    private static final String VALOR_DESCONTO_PRODUTO = "valorDesconto";
    private static final String IVA_ID_PRODUTO = "iva_id";

    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProdutoTable = "CREATE TABLE " + TABLE_PRODUTO_NAME + " (" + ID_PRODUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOME_PRODUTO + " TEXT NOT NULL, " + DESCRICAO_PRODUTO + " TEXT NOT NULL, " + DESCRICAO_GERAL_PRODUTO + " TEXT NOT NULL, " + FOTO_PRODUTO + " TEXT NOT NULL, " + QUANTSTOCK_PRODUTO + " INTEGER NOT NULL," + PONTOS_PRODUTO + " INTEGER," + VALOR_DESCONTO_PRODUTO + " DECIMAL," + PRECO_PRODUTO + " DECIMAL NOT NULL," + SUBCATEGORIA_ID_PRODUTO + " INTEGER NOT NULL," + IVA_ID_PRODUTO + " INTEGER NOT NULL" +");";
        db.execSQL(createProdutoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO_NAME);
        this.onCreate(db);
    }

    public ArrayList<Produto> getAllProdutosDB()
    {
        ArrayList<Produto> produtos = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_PRODUTO_NAME, new String[]{
                ID_PRODUTO, NOME_PRODUTO, DESCRICAO_GERAL_PRODUTO, DESCRICAO_PRODUTO, FOTO_PRODUTO, PONTOS_PRODUTO, SUBCATEGORIA_ID_PRODUTO, IVA_ID_PRODUTO, VALOR_DESCONTO_PRODUTO, PRECO_PRODUTO, QUANTSTOCK_PRODUTO
        }, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getDouble(9), cursor.getDouble(10));
                auxProduto.setId(cursor.getInt(0));
                produtos.add(auxProduto);
            }while(cursor.moveToNext());
        }
        return produtos;
    }
}
