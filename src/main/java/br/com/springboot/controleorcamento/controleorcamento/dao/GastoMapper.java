package br.com.springboot.controleorcamento.controleorcamento.dao;


import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import br.com.springboot.controleorcamento.controleorcamento.model.GastoCategorizado;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GastoMapper implements RowMapper<Gasto> {

    @Override
    public Gasto mapRow(ResultSet resultSet, int i) throws SQLException {
        Gasto gasto = new Gasto();
        gasto.setDescricao(resultSet.getString("gdescricao"));
        gasto.setId(resultSet.getLong("gid"));
        gasto.setData(resultSet.getDate("gdata").toLocalDate());

        Categoria categoria = new Categoria();
        categoria.setDescricao(resultSet.getString("cdescricao"));
        categoria.setId(resultSet.getLong("cid"));

        GastoCategorizado gastoCategorizado = new GastoCategorizado();
        gastoCategorizado.setCategoria(categoria);
        gastoCategorizado.setValor(resultSet.getBigDecimal("gcvalor"));
        gastoCategorizado.setId(resultSet.getLong("gcid"));

        gasto.adicionaGastoCategorizado(gastoCategorizado);

        return gasto;
    }
}
