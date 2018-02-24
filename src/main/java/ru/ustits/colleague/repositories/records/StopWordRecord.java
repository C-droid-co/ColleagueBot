package ru.ustits.colleague.repositories.records;

import lombok.*;

import javax.persistence.*;

/**
 * @author ustits
 */
@Entity
@Table(name = "stopwords")
@Value
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@EqualsAndHashCode(exclude = "id")
public final class StopWordRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String word;

}
