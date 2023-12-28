package com.example.project3355.coulmn.entity;


import com.example.project3355.board.entity.Board;
import com.example.project3355.coulmn.dto.ColumnsRequestDto;
import com.example.project3355.coulmn.dto.ColumnsSequenceDto;
import com.example.project3355.global.common.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@Getter
@NoArgsConstructor
@Table(name="columns")
public class Columns extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false,length = 50)
  private String columnTitle;
  @Column(nullable = false)
  private Integer sequence;


  @ManyToOne
  @JoinColumn(name = "board_id",nullable = false)
  private Board board;


  public Columns(ColumnsRequestDto columnsRequestDto,Board board){
    this.columnTitle=columnsRequestDto.getColumnTitle();
    this.board=board;
  }


  public void update(ColumnsRequestDto columnsRequestDto){
    this.columnTitle=columnsRequestDto.getColumnTitle();
  }

  public void addSequence(ColumnsSequenceDto sequenceDto){
    this.sequence=sequenceDto.getSequence();
  }


}