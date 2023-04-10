-- SELECT *
-- -- f.FacilityName,
-- -- TIMESTAMPDIFF(year, t.DateofTreatment, CURDATE()) AS 'yearsOfOperation',
--                     -- "f.MonthlyWaterQuantity
-- FROM treatmentFacility AS f INNER JOIN treatment AS t
-- ON f.FacilityID = t.FacilityID
-- ORDER BY f.FacilityName;

-- join with aggregate functions
-- SELECT
-- 	MIN(f.FacilityName) AS 'FacilityName',
--     TIMESTAMPDIFF(year, IFNULL(MIN(t.DateOfTreatment), CURDATE()), CURDATE()) AS 'yearsOfOperation',
--     MIN(f.MonthlyWaterQuantity) AS 'MonthyWaterQuantity'
-- FROM treatmentFacility f LEFT OUTER JOIN treatment t
-- ON f.FacilityID = t.FacilityID AND t.DateOfTreatment <= CURDATE()
-- GROUP BY f.FacilityID
-- ORDER BY f.FacilityName;

-- join to derived table of statistics
SELECT
	f.FacilityName AS 'FacilityName',
    IFNULL(stats.yearsOperational,0) AS 'yearsOfOperation',
    f.MonthlyWaterQuantity AS 'MonthyWaterQuantity'
FROM treatmentFacility f
LEFT OUTER JOIN 
(
	SELECT
		FacilityID,
		TIMESTAMPDIFF(year, MIN(DateOfTreatment), CURDATE()) AS 'yearsOperational'
	FROM treatment t
	WHERE DateOfTreatment <= CURDATE()
	GROUP BY FacilityID
) stats
ON f.FacilityID = stats.FacilityID 
ORDER BY f.FacilityName;


-- join to CTE of statistics
-- WITH 
-- stats1 AS (
-- 	SELECT
-- 		FacilityID,
-- 		MIN(DateOfTreatment) AS 'FirstHistoricalTreatment'
-- 	FROM treatment t
-- 	WHERE DateOfTreatment <= CURDATE()
-- 	GROUP BY FacilityID
-- ),
-- stats2 AS (
-- 	SELECT
-- 		FacilityID,
-- 		TIMESTAMPDIFF(year, FirstHistoricalTreatment, CURDATE()) AS 'yearsOperational'
-- 	FROM stats1
-- )
-- SELECT
-- 	f.FacilityName AS 'FacilityName',
--     IFNULL(stats.yearsOperational,0) AS 'yearsOfOperation',
--     f.MonthlyWaterQuantity AS 'MonthyWaterQuantity'
-- FROM treatmentFacility f
-- LEFT OUTER JOIN stats2 ON f.FacilityID = stats.FacilityID 
-- ORDER BY f.FacilityName;




-- correlated sub-query
-- SELECT
-- 	f.FacilityName AS 'FacilityName',
--     IFNULL((SELECT TIMESTAMPDIFF(year, MIN(DateOfTreatment), CURDATE()) FROM treatment where FacilityId = f.FacilityID AND DateOfTreatment <= CURDATE()),0) AS 'yearsOfOperation',
--     f.MonthlyWaterQuantity AS 'MonthyWaterQuantity'
-- FROM treatmentFacility f
-- ORDER BY f.FacilityName;

