package br.com.springboot.controleorcamento.controleorcamento.dao;


import br.com.springboot.controleorcamento.controleorcamento.model.Categoria;
import br.com.springboot.controleorcamento.controleorcamento.model.Despesa;
import br.com.springboot.controleorcamento.controleorcamento.model.DespesaCategorizada;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GastoMapper implements RowMapper<Despesa> {

    @Override
    public Despesa mapRow(ResultSet resultSet, int i) throws SQLException {
        Despesa gasto = new Despesa();
        gasto.setDescricao(resultSet.getString("gdescricao"));
        gasto.setId(resultSet.getLong("gid"));
        gasto.setData(resultSet.getDate("gdata").toLocalDate());

        Categoria categoria = new Categoria();
        categoria.setDescricao(resultSet.getString("cdescricao"));
        categoria.setId(resultSet.getLong("cid"));

        DespesaCategorizada despesaCategorizada = new DespesaCategorizada();
        despesaCategorizada.setCategoria(categoria);
        despesaCategorizada.setValor(resultSet.getBigDecimal("gcvalor"));
        despesaCategorizada.setId(resultSet.getLong("gcid"));

        gasto.adicionaGastoCategorizado(despesaCategorizada);

        return gasto;
    }
}
