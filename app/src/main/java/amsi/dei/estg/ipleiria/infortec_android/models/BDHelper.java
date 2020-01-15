package amsi.dei.estg.ipleiria.infortec_android.models;

import android.content.ContentValues;
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

    /*
        Elementos para a tabela Produtos
     */
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

    /*
        Elementos para a tabela User
     */
    private static final String TABLE_USER_NAME = "User";
    private static final String ID_USER = "id";
    private static final String USERNAME = "username";
    private static final String AUTH_KEY = "auth_key";
    private static final String PASSWORD_HASH = "password_hash";
    private static final String PASSWORD_RESET_TOKEN = "password_reset_token";
    private static final String EMAIL = "email";
    private static final String STATUS = "status";
    private static final String ROLE = "role";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";
    private static final String VERIFICATION_TOKEN = "verification_token";

    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProdutoTable = "CREATE TABLE " + TABLE_PRODUTO_NAME + " (" + ID_PRODUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOME_PRODUTO + " TEXT NOT NULL, " + DESCRICAO_PRODUTO + " TEXT NOT NULL, " + DESCRICAO_GERAL_PRODUTO + " TEXT NOT NULL, " + FOTO_PRODUTO + " TEXT NOT NULL, " + QUANTSTOCK_PRODUTO + " INTEGER NOT NULL," + PONTOS_PRODUTO + " INTEGER," + VALOR_DESCONTO_PRODUTO + " DECIMAL," + PRECO_PRODUTO + " DECIMAL NOT NULL," + SUBCATEGORIA_ID_PRODUTO + " INTEGER NOT NULL," + IVA_ID_PRODUTO + " INTEGER NOT NULL" +");";
        String createUserTable = "CREATE TABLE " + TABLE_USER_NAME + " (" + ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT NOT NULL, " + AUTH_KEY + " TEXT NOT NULL, " + PASSWORD_HASH + " TEXT NOT NULL, " + PASSWORD_RESET_TOKEN + " TEXT NOT NULL, " + EMAIL + " TEXT NOT NULL, " + STATUS + " INTEGER," + ROLE + " INTEGER NOT NULL, " + CREATED_AT + " INTEGER NOT NULL, " + UPDATED_AT + " INTEGER NOT NULL, " + VERIFICATION_TOKEN + " TEXT"  +");";
        db.execSQL(createProdutoTable);
        db.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        this.onCreate(db);
    }

    public ArrayList<Produto> getAllProdutosDB()
    {
        ArrayList<Produto> produtos = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_PRODUTO_NAME, new String[]{
                ID_PRODUTO, QUANTSTOCK_PRODUTO, PONTOS_PRODUTO, SUBCATEGORIA_ID_PRODUTO, IVA_ID_PRODUTO, NOME_PRODUTO, FOTO_PRODUTO, DESCRICAO_PRODUTO, DESCRICAO_GERAL_PRODUTO, PRECO_PRODUTO, VALOR_DESCONTO_PRODUTO
        }, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                //Produto auxProduto = new Produto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getDouble(9), cursor.getDouble(10));
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getDouble(9), cursor.getDouble(10));
                auxProduto.setId(cursor.getInt(0));
                System.out.println("---> Ree1 (auxProduto): " + auxProduto);
                produtos.add(auxProduto);
            }while(cursor.moveToNext());
        }
        System.out.println("---> Ree2 (cursor): " + cursor);
        System.out.println("---> Ree3 (produtos): " + produtos);
        return produtos;
    }

    public void adicionarProdutoBD(Produto produto)
    {
        ContentValues values = new ContentValues();
        values.put(ID_PRODUTO, produto.getId());
        values.put(NOME_PRODUTO, produto.getNome());
        values.put(DESCRICAO_PRODUTO, produto.getDescricao());
        values.put(DESCRICAO_GERAL_PRODUTO, produto.getDescricaoGeral());
        values.put(FOTO_PRODUTO, produto.getFotoProduto());
        values.put(QUANTSTOCK_PRODUTO, produto.getQuantStock());
        values.put(PONTOS_PRODUTO, produto.getPontos());
        values.put(SUBCATEGORIA_ID_PRODUTO, produto.getSubCategoria_id());
        values.put(PRECO_PRODUTO, produto.getPreco());
        values.put(VALOR_DESCONTO_PRODUTO, produto.getValorDesconto());
        values.put(IVA_ID_PRODUTO, produto.getIva_id());


        this.database.insert(TABLE_PRODUTO_NAME, null, values);
    }

    public void removerAllProdutosDB()
    {
        this.database.delete(TABLE_PRODUTO_NAME, null, null);
    }
}
