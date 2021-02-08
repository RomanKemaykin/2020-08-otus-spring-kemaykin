package ru.diasoft.digitalq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "team_lead_name")
    private String teamLeadName;

    @Column(name = "product_owner_name")
    private String productOwnerName;

    @Column(name = "product_center_name")
    private String productCenterName;

    @Column(name = "curator_name")
    private String curatorName;

    @OneToMany(targetEntity = TeamMemberEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "team_id")
    private List<TeamMemberEntity> teamMemberEntityList;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Override
    public String toString() {
        return "Team: id = " + this.id + ", name = " + this.name +
                ", teamLeadName = " + this.teamLeadName +
                ", productOwnerName = " + this.productOwnerName +
                ", productCenterName = " + this.productCenterName +
                ", date_start = " + this.dateStart +
                ", date_end = " + this.dateEnd;
    }
}

