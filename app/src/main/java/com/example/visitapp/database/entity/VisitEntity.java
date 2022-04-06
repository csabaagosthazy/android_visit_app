package com.example.visitapp.database.entity;

import androidx.annotation.NonNull;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;


public class VisitEntity implements Comparable{

        private String visitId;
        private String description;
        private Long visitDate;
        private String visitor;
        private String hostId;


        public VisitEntity(){
        }

        public VisitEntity(String description, Long visitDate, String visitor, String employee) {
                this.description = description;
                this.visitDate = visitDate;
                this.visitor = visitor;
        }

        @Exclude
        public String getVisitId() {
                return visitId;
        }

        public void setVisitId(String visitId) {
                this.visitId = visitId;
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

        @Exclude
        public String getHostId() {
                return hostId;
        }

        public void setHostId(String hostId) {
                this.hostId = hostId;
        }

        @Override
        public boolean equals(Object obj) {
                if (obj == null ) return false;
                if (obj == this) return true;
                if (!(obj instanceof VisitorEntity)) return false;
                VisitEntity that = (VisitEntity) obj;
                return getVisitId().equals(that.getVisitId());
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

                return result;
        }
}
