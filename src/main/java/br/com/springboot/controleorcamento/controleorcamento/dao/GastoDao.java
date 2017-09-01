package br.com.springboot.controleorcamento.controleorcamento.dao;

import br.com.springboot.controleorcamento.controleorcamento.model.Gasto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GastoDao {

    private  final JdbcTemplate jdbcTemplate;

    @Autowired
    public GastoDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Gasto> findGastoPorCategoria(String descricao){

        StringBuilder sql = new StringBuilder();

        sql.append("select g.descricao gdescricao,g.id gid,g.data gdata,c.descricao cdescricao,c.id cid,gc.valor gcvalor,gc.id gcid from gasto g ");
        sql.append("join gasto_gastos_categorizados ggc on g.id = ggc.gasto_id ");
        sql.append("join gasto_categorizado gc on ggc.gastos_categorizados_id = gc.id ");
        sql.append("join categoria c on gc.categoria_id = c.id ");
        sql.append("where c.descricao like ? ");

        return jdbcTemplate.query(sql.toString(), new GastoMapper(), descricao);

    }


}
