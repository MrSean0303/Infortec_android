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
    private static final String NOME = "nome";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String STATUS = "status";
    private static final String NIF = "nif";
    private static final String MORADA = "morada";
    private static final String NUMPONTOS = "numPontos";



    /*
        Elementos para a tabela Venda
    */
    private static final String TABLE_VENDA_NAME = "Venda";
    private static final String ID_VENDA = "id";
    private static final String DATA = "data";
    private static final String TOTAL = "total";
    private static final String USER_ID = "user_id";//TABLE_LINHAVENDA_NAME

    /*
        Elementos para a tabela LinhaVenda
    */
    private static final String TABLE_LINHAVENDA_NAME = "LinhaVenda";
    private static final String ID_LINHAVENDA = "id";
    private static final String QUANTIDADE = "quantidade";
    private static final String ISPONTOS = "isPontos";
    private static final String PRECO = "preco";
    private static final String VENDA_ID = "venda_id";
    private static final String PRODUTO_ID = "produto_id";

    /*
    Elementos para a tabela Favorito
*/
    private static final String TABLE_FAVORITO_NAME = "Favorito";
    private static final String ID_FAVORITO = "idFavorito";
    private static final String PRODUTO_ID_FAVORITO = "produto_id";
    private static final String USER_ID_FAVORITO = "utilizador_id";


    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProdutoTable = "CREATE TABLE " + TABLE_PRODUTO_NAME + " (" + ID_PRODUTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOME_PRODUTO + " TEXT NOT NULL, " + DESCRICAO_PRODUTO + " TEXT NOT NULL, " + DESCRICAO_GERAL_PRODUTO + " TEXT NOT NULL, " + FOTO_PRODUTO + " TEXT NOT NULL, " + QUANTSTOCK_PRODUTO + " INTEGER NOT NULL," + PONTOS_PRODUTO + " INTEGER," + VALOR_DESCONTO_PRODUTO + " DECIMAL(10,2)," + PRECO_PRODUTO + " DECIMAL(10,2) NOT NULL," + SUBCATEGORIA_ID_PRODUTO + " INTEGER NOT NULL," + IVA_ID_PRODUTO + " INTEGER NOT NULL" +");";
        String createUserTable = "CREATE TABLE " + TABLE_USER_NAME + " (" + ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOME + " TEXT NOT NULL, " + USERNAME + " TEXT NOT NULL, " + EMAIL + " TEXT NOT NULL, " + STATUS + " INTEGER," + NIF + " TEXT NOT NULL, " + NUMPONTOS + " INTEGER NOT NULL, " + MORADA + " TEXT"  +");";
        String createVendaTable = "CREATE TABLE " + TABLE_VENDA_NAME + " (" + ID_VENDA + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TOTAL + " DECIMAL NOT NULL, "+ DATA + " TEXT NOT NULL, "  + USER_ID + " INTEGER NOT NULL" +");";
        String createLinhaVendaTable = "CREATE TABLE " + TABLE_LINHAVENDA_NAME + " (" + ID_LINHAVENDA + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ QUANTIDADE + " INTEGER NOT NULL, "+ ISPONTOS + " INTEGER, "  + PRECO + " DECIMAL NOT NULL, " + VENDA_ID + " INTEGER NOT NULL, "+ PRODUTO_ID + " INTEGER NOT NULL" +");";
        String createFavoritoTable = "CREATE TABLE " + TABLE_FAVORITO_NAME + " (" + ID_FAVORITO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRODUTO_ID_FAVORITO + " INTEGER NOT NULL, " + USER_ID_FAVORITO + " INTEGER NOT NULL" + ");";

        db.execSQL(createProdutoTable);
        db.execSQL(createUserTable);
        db.execSQL(createVendaTable);
        db.execSQL(createLinhaVendaTable);
        db.execSQL(createFavoritoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTO_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDA_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINHAVENDA_NAME );
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
                Produto auxProduto = new Produto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getDouble(9), cursor.getDouble(10));
                auxProduto.setId(cursor.getInt(0));
                produtos.add(auxProduto);
            }while(cursor.moveToNext());
        }
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


    public void adicionarFavoritoBD(Favorito favorito)
    {
        ContentValues values = new ContentValues();
        values.put(ID_FAVORITO, favorito.getIdFavorito());
        values.put(PRODUTO_ID_FAVORITO, favorito.getProduto_id());
        values.put(USER_ID_FAVORITO, favorito.getUtilizador_id());

        this.database.insert(TABLE_FAVORITO_NAME, null, values);
    }

    public void removerFavoritoBD(String id_produto)
    {
        this.database.delete(TABLE_FAVORITO_NAME,PRODUTO_ID_FAVORITO+"=?",new String[]{id_produto});
    }

    public void removerAllProdutosDB()
    {
        this.database.delete(TABLE_PRODUTO_NAME, null, null);
    }

    //USER
    public User getUserBD(){
        User auxUser = new User(0,"","","",0,"","",0);;
        Cursor cursor = this.database.query(TABLE_USER_NAME, new String[]{
                ID_USER, NOME,USERNAME, EMAIL, STATUS, MORADA, NIF, NUMPONTOS,
        }, null, null, null, null, null);

        if(cursor.moveToFirst()) {
            auxUser = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7));
            auxUser.setId(cursor.getInt(0));
        }
        return auxUser;
    }

    public void adicionarUserBD(User user)
    {
        ContentValues values = new ContentValues();
        values.put(ID_USER, user.getId());
        values.put(NOME, user.getNome());
        values.put(USERNAME, user.getUsername());
        values.put(EMAIL, user.getEmail());
        values.put(STATUS, user.getStatus());
        values.put(NIF, user.getNif());
        values.put(MORADA, user.getMorada());
        values.put(NUMPONTOS, user.getNumPontos());

        this.database.insert(TABLE_USER_NAME, null, values);
    }

    public void removerAllUsersDB()
    {
        this.database.delete(TABLE_USER_NAME, null, null);
    }

    //Venda
    public ArrayList<Venda> getVenda(){

        ArrayList<Venda> vendas = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_VENDA_NAME, new String[]{
                ID_USER, TOTAL, DATA, USER_ID
        }, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                //Produto user = new Produto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getDouble(9), cursor.getDouble(10));
                Venda auxVenda = new Venda(cursor.getInt(0), cursor.getDouble(1), cursor.getString(2), cursor.getInt(3));
                auxVenda.setId(cursor.getInt(0));
                System.out.println("---> Ree1 (auxVenda): " + auxVenda);
                vendas.add(auxVenda);
            }while(cursor.moveToNext());
        }
        System.out.println("---> Ree2 (cursor): " + cursor);
        System.out.println("---> Ree3 (vendas): " + vendas);
        return vendas;
    }

    public void adicionarVendaBD(Venda venda)
    {
        ContentValues values = new ContentValues();
        values.put(ID_USER, venda.getId());
        values.put(TOTAL, venda.getTotalVenda());
        values.put(DATA, venda.getData());
        values.put(USER_ID, venda.getUser_id());

        this.database.insert(TABLE_VENDA_NAME, null, values);
    }

    public void removerVendaDB()
    {
        this.database.delete(TABLE_VENDA_NAME, null, null);
    }

    //LinhaVenda
    public ArrayList<LinhaVenda> getLinhaVenda(){

        ArrayList<LinhaVenda> LinhaVendas = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_LINHAVENDA_NAME, new String[]{
                ID_USER, TOTAL, DATA, USER_ID
        }, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                //Produto user = new Produto(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getDouble(9), cursor.getDouble(10));
                LinhaVenda auxlinhaVendas = new LinhaVenda(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getDouble(3), cursor.getInt(4), cursor.getInt(5));
                auxlinhaVendas.setId(cursor.getInt(0));
                System.out.println("---> Ree1 (auxlinhaVendas): " + auxlinhaVendas);
                LinhaVendas.add(auxlinhaVendas);
            }while(cursor.moveToNext());
        }
        System.out.println("---> Ree2 (cursor): " + cursor);
        System.out.println("---> Ree3 (LinhaVendas): " + LinhaVendas);
        return LinhaVendas;
    }

    public void adicionarLinhaVendaBD(LinhaVenda linhavenda)
    {
        ContentValues values = new ContentValues();
        values.put(ID_USER, linhavenda.getId());
        values.put(QUANTIDADE, linhavenda.getQuantidade());
        values.put(ISPONTOS, linhavenda.getIsPontos());
        values.put(PRECO, linhavenda.getPreco());
        values.put(VENDA_ID, linhavenda.getVenda_id());
        values.put(PRODUTO_ID, linhavenda.getProduto_id());

        this.database.insert(TABLE_LINHAVENDA_NAME, null, values);
    }

    public void removerLinhaVendaDB()
    {
        this.database.delete(TABLE_LINHAVENDA_NAME, null, null);
    }
}
