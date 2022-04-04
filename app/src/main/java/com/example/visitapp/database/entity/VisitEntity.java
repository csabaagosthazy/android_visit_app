package com.example.visitapp.database.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;


public class VisitEntity implements Comparable{

        private String idVisit;
        private String description;
        private Long visitDate;
        private String visitor;
        private String employee;


        public VisitEntity(){
        }

        public VisitEntity(String description, Long visitDate, String visitor, String employee) {
                this.description = description;
                this.visitDate = visitDate;
                this.visitor = visitor;
                this.employee = employee;
        }

        @Exclude
        public String getIdVisit() {
                return idVisit;
        }

        public void setIdVisit(String idVisit) {
                this.idVisit = idVisit;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public Long getVisitDate() {
                return visitDate;
        }

        public void setVisitDate(Long visitDate) {
                this.visitDate = visitDate;
        }

        public String getVisitor() {
                return visitor;
        }

        public void setVisitor(String visitor) {
                this.visitor = visitor;
        }

        public String getEmployee() {
                return employee;
        }

        public void setEmployee(String employee) {
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

        @Override
        public String toString() {
                return description + " ";
        }

        @Override
        public int compareTo(@NonNull Object o) {
                return toString().compareTo(o.toString());
        }

        @Exclude
        public Map<String, Object> toMap() {
                HashMap<String, Object> result = new HashMap<>();
                result.put("description", description);
                result.put("visitDate", visitDate);
                result.put("visitor", visitor);
                result.put("employee", employee);

                return result;
        }
}
