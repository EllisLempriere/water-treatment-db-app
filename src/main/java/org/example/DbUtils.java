package org.example;

import org.example.BusinessModels.BusinessStats;
import org.example.BusinessModels.TreatmentFacility;
import org.example.BusinessModels.TreatmentFacilitySummaryReportLine;
import org.example.BusinessModels.WaterUsageStats;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtils implements IDbUtils {

    private final String dbUserId = "<Enter User Id Here>";
    private final String dbUserPass = "<Enter User Password Here>";
    private final String dbUrl = "jdbc:mysql:<Enter JDBC URI Address HEre>";


    @Override
    public boolean canConnectToDB(String userId, String userPass) {
        boolean canConnect = false;

        try (Connection conn = openConnection(userId, userPass)) {
            canConnect = true;
        } catch (SQLException ignored) {
        }

        return canConnect;
    }


    @Override
    public BusinessStats getIndustrialBusinessStats() {
        BusinessStats industrialBusinessStats = null;

        try (Connection conn = openConnection(dbUserId, dbUserPass)) {
            String query =
                    "SELECT SUM(AverageMonthlyConsumption) AS 'totalWaterConsumption', " +
                            "SUM(AverageMonthlyWasteGeneration) AS 'totalWasteGeneration' " +
                    "FROM business " +
                    "WHERE BusinessType = 'Industrial' " +
                    "GROUP BY BusinessType";
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            results.next();

            industrialBusinessStats = new BusinessStats(results.getInt("totalWaterConsumption"), results.getInt("totalWasteGeneration"));
        } catch (SQLException ignored) {
        }

        return industrialBusinessStats;
    }



    public List<TreatmentFacilitySummaryReportLine> getTreatmentFacilitiesSummaryReport() {
        List<TreatmentFacilitySummaryReportLine> treatmentFacilitiesReport = null;

        try (Connection conn = openConnection(dbUserId, dbUserPass)) {
            String query =
                    "SELECT " +
                        "f.FacilityName AS 'FacilityName', " +
                        "IFNULL(stats.yearsOperational,0) AS 'YearsOfOperation', " +
                        "f.MonthlyWaterQuantity AS 'MonthlyWaterQuantity' " +
                    "FROM treatmentFacility f " +
                    "LEFT OUTER JOIN " +
                    "(" +
                        "SELECT " +
                        "FacilityID, " +
                        "TIMESTAMPDIFF(year, MIN(DateOfTreatment), CURDATE()) AS 'YearsOperational' " +
                        "FROM treatment t " +
                        "WHERE DateOfTreatment <= CURDATE() " +
                        "GROUP BY FacilityID " +
                    ") stats " +
                    "ON f.FacilityID = stats.FacilityID " +
                    "ORDER BY f.FacilityName";

            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            treatmentFacilitiesReport = new ArrayList<>();
            while (results.next()) {
                TreatmentFacilitySummaryReportLine line =
                        new TreatmentFacilitySummaryReportLine(results.getString("FacilityName"),
                                results.getInt("YearsOfOperation"), results.getInt("MonthlyWaterQuantity"));
                treatmentFacilitiesReport.add(line);
            }
        } catch (SQLException ignored) {
        }

        return treatmentFacilitiesReport;
    }


    public List<TreatmentFacility> getTreatmentFacilities() {
        List<TreatmentFacility> treatmentFacilities = null;

        try (Connection conn = openConnection(dbUserId, dbUserPass)) {
            String query =
                    "SELECT * " +
                    "FROM treatmentFacility " +
                    "ORDER BY FacilityID";
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            treatmentFacilities = new ArrayList<>();
            while (results.next()) {
                TreatmentFacility f = new TreatmentFacility();
                f.facilityId = results.getInt("FacilityID");
                f.facilityName = results.getString("FacilityName");
                f.facilityAddress = results.getString("FacilityAddress");
                f.contactName = results.getString("ContactName");
                f.contactPhone = results.getString("ContactPhone");
                f.monthlyWaterQuantity = results.getInt("MonthlyWaterQuantity");
                f.waterSourceId = results.getInt("WaterSourceID");
                treatmentFacilities.add(f);
            }
        } catch (SQLException ignored) {
        }

        return treatmentFacilities;
    }

    public void updateTreatmentFacility(TreatmentFacility facility) {
        try (Connection conn = openConnection(dbUserId, dbUserPass)) {
            String query =
                    "UPDATE treatmentFacility " +
                        "SET " +
                            "MonthlyWaterQuantity = ? " +
                        "WHERE FacilityID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, facility.monthlyWaterQuantity);
            stmt.setInt(2, facility.facilityId);

            stmt.execute();
            int rowsUpdated = stmt.getUpdateCount();
            if (rowsUpdated < 1)
                throw new UnsupportedOperationException("Update resulted in no rows changed");
            else if (rowsUpdated > 1)
                throw new UnsupportedOperationException("Update resulted in multiple rows changed");

        } catch (SQLException e) {
            throw new UnsupportedOperationException("Unable to update", e);
        }
    }

    public WaterUsageStats getBusinessWaterUsageStats() {
        WaterUsageStats stats = null;

        try (Connection conn = openConnection(dbUserId, dbUserPass)) {
            String query =
                    "SELECT " +
                        "AVG(AverageMonthlyConsumption) AS avgWaterConsumption, " +
                        "AVG(AverageMonthlyWasteGeneration) AS avgWasteGeneration, " +
                        "SUM(AverageMonthlyWasteGeneration) AS waterNeedingTreatment " +
                    "FROM business";
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery(query);

            results.next();
            stats = new WaterUsageStats(results.getDouble("avgWaterConsumption"),
                    results.getDouble("avgWasteGeneration"),
                    results.getDouble("waterNeedingTreatment"));
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return stats;
    }


    private Connection openConnection(String id, String pass) throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbUrl, id, pass);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }

        return conn;
    }
}
