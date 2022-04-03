package com.example.myfirstapp.database.entity;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myfirstapp.database.Converters;

import java.util.Date;

@Entity(tableName = "visits",
        foreignKeys ={
        @ForeignKey(
                entity = PersonEntity.class,
                parentColumns = "idPerson",
                childColumns = "employee",
                onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = PersonEntity.class,
                parentColumns = "idPerson",
                childColumns = "visitor",
                onDelete = ForeignKey.CASCADE
        )},
        indices = {
        @Index(
                value = {"employee"}
        ),
        @Index(
                value = {"visitor"}
        )}
)
public class VisitEntity implements Comparable{
        @PrimaryKey(autoGenerate = true)
        private Long idVisit;
        @ColumnInfo(name = "description")
        private String description;
        @ColumnInfo(name = "visit_date")
        @TypeConverters(Converters.class)
        private Date visitDate;
        @ColumnInfo(name = "visitor")
        private Long visitor;
        @ColumnInfo(name = "employee")
        private Long employee;

        @Ignore
        public VisitEntity(){

        }

        public VisitEntity(String description, Date visitDate, Long visitor, Long employee) {
                this.description = description;
                this.visitDate = visitDate;
                this.visitor = visitor;
                this.employee = employee;
        }

        public Long getIdVisit() {
                return idVisit;
        }

        public void setIdVisit(Long idVisit) {
                this.idVisit = idVisit;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Date getVisitDate() {
                return visitDate;
        }

        public void setVisitDate(Date visitDate) {
                this.visitDate = visitDate;
        }

        public Long getVisitor() {
                return visitor;
        }

        public void setVisitor(Long visitor) {
                this.visitor = visitor;
        }

        public Long getEmployee() {
                return employee;
        }

        public void setEmployee(Long employee) {
                this.employee = employee;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj == null ) return false;
                if (obj == this) return true;
                if (!(obj instanceof PersonEntity)) return false;
                VisitEntity that = (VisitEntity) obj;
                return getIdVisit().equals(that.getIdVisit());
        }
        @NonNull
        @Override
        public String toString() {
                return description + " ";
        }

        @Override
        public int compareTo(@NonNull Object o) {
                return toString().compareTo(o.toString());
        }
}
