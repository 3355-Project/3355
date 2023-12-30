package com.example.project3355.coulmn.entity;


import com.example.project3355.board.entity.Board;
import com.example.project3355.card.entity.Card;
import com.example.project3355.coulmn.dto.ColumnsRequestDto;
import com.example.project3355.coulmn.dto.ColumnsSequenceDto;
import com.example.project3355.global.common.Timestamped;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "board_id",nullable = false)
  private Board board;

  @OneToMany(mappedBy = "columns", cascade = CascadeType.REMOVE)
  private List<Card> cardList = new ArrayList<>();


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
