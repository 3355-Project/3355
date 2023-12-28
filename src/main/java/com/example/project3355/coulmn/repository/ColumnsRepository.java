package com.example.project3355.coulmn.repository;

import com.example.project3355.coulmn.entity.Columns;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns,Long> {

  Long countByBoardId(Long boardId);

  List<Columns> findByBoardIdAndSequenceBetween(Long boardId,Integer first,Integer last);
}
