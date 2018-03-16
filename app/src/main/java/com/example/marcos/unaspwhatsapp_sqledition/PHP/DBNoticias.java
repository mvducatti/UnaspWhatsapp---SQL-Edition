package com.example.marcos.unaspwhatsapp_sqledition.PHP;

import android.support.v7.app.AppCompatActivity;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBNoticias {

    private int id;
    private int idUser;
    public String newsTitle;
    public String newsPost;
    public boolean _status;
    public String _message;



    public DBNoticias(){
        this.id = -1;
        this.newsTitle = "";
        this.newsPost = "";

    }

    public DBNoticias(AppCompatActivity activity) {

    }

    public ArrayList<DBNoticias> getNewsList(){
        ArrayList<DBNoticias> lista = new ArrayList<>();
        try {
            ResultSet resultSet = DB.select("SELECT * FROM news");
            if (resultSet != null){
                while (resultSet.next()){

                    DBNoticias obj = new DBNoticias();

                    obj.setId(resultSet.getInt("id_news"));
                    obj.setNewsTitle(resultSet.getString("news_title"));
                    obj.setNewsPost(resultSet.getString("news_message"));
                    lista.add(obj);
                }
            }
        }catch (Exception ex){
            this._message = ex.getMessage();
            this._status = false;
            this._status = false;
        }
        return lista;
    }

    public void salvar() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String comando = "";
        if (this.getId() == -1){


            comando = String.format("INSERT INTO news (news_title, news_message, fk_user_id) VALUES ('%s','%s',%d);",
                    this.getNewsTitle(), this.getNewsPost(), this.idUser);
        }else {
            comando = String.format("UPDATE news SET news_title ='%s', news_post = '%s', id_news = %d WHERE id = %d;",
                    this.getNewsTitle(), this.getNewsPost(), this.getId());
        }
        DB db =  new DB();
        db.executeQuery(comando);
    }

    public void apagar() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        String comando = String.format("DELETE FROM news WHERE id = %d;", this.getId());

        DB db =  new DB();
        db.execute(comando);
    }

    public int getId() {
        return id;
    }

    /** Id of News
     *
     * @param id only int
     */
    public void setId(int id) {
        this.id = id;
    }

    /** id User
     *
     * @param id
     */
    public void setIdUser(int id) {
        this.idUser = id;
    }


    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsPost() {
        return newsPost;
    }

    public void setNewsPost(String newsPost) {
        this.newsPost = newsPost;
    }
}
