import groovy.io.FileType

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.attribute.BasicFileAttributes
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class MoveFilesScript {

    private static class YearsAndMonths {
        int years
        int months
    }

    private static List<String> applyFilesByPeriodScript(String folderPath, String filesExt, String dateFrom) {
        List<String> result = []

        if (dateFrom == null) {
            throw new IllegalArgumentException("Parameter startDate is absent")
        }

        LocalDateTime periodStartDate = LocalDateTime.ofInstant(new SimpleDateFormat("dd.MM.yyyy")
                .parse(dateFrom).toInstant(), ZoneId.systemDefault())

        Map<String, List<File>> filesByMonth = [:] as TreeMap

        def filesExtArr = filesExt.split('\\|')

        new File(folderPath).eachFile(FileType.FILES) {
            filesExtArr.each { ext ->
                if (it.name.endsWith(ext)) {
                    def attrs = Files.readAttributes(it.toPath(), BasicFileAttributes.class)

                    def fileCreationDate = LocalDate.ofInstant(Instant.ofEpochMilli(
                            attrs.creationTime().toMillis()), ZoneId.systemDefault()).atStartOfDay()

                    if (fileCreationDate >= periodStartDate) {
                        def folderName = getFormattedYearsAndMonthsString(periodStartDate, fileCreationDate).toString()

                        if (filesByMonth[folderName] == null) {
                            filesByMonth[folderName] = [it]
                        } else {
                            filesByMonth[folderName] << it
                        }
                    }
                }
            }

        }

        filesByMonth.each {
            def folderName = it.key

            it.value.each { file ->
                def fileToMove = file as File
                def newFolder = new File(folderPath + File.separator + folderName)

                if (newFolder.mkdir()) {
                    println("Folder ${newFolder.getName()} was created...")
                }
                def newFile = new File(folderPath + File.separator + folderName).toPath().resolve(fileToMove.getName())

                if (!newFile.toFile().exists()) {
                    Files.move(fileToMove.toPath(), newFile, StandardCopyOption.REPLACE_EXISTING)
                    println("File ${newFile} was moved successfully...")

                    result << folderName.toString()
                }
            }

        }

        result.size() > 0 ?: println("No files were moved by script")

        result
    }

    static List<String> applyFilesByMonthsScript(String folderPath, String filesExt) {
        List<String> result = []

        def dateFormatSymbols = new DateFormatSymbols(new Locale("ru", "RU"))
        dateFormatSymbols.setMonths(["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
                                     "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"] as String[])
        def outputFormat = new SimpleDateFormat("yyyy, MMMM", dateFormatSymbols)

        Map<LocalDateTime, List<File>> filesByMonth = [:] as TreeMap

        def filesExtArr = filesExt.split('\\|')

        new File(folderPath).eachFile(FileType.FILES) {
            filesExtArr.each { ext ->
                if (it.name.endsWith(ext)) {

                    def attrs = Files.readAttributes(it.toPath(), BasicFileAttributes.class)

                    def fileCreationDate = LocalDate.ofInstant(Instant.ofEpochMilli(attrs.creationTime().toMillis()), ZoneId.systemDefault())
                    def monthDate = fileCreationDate.withDayOfMonth(1).atStartOfDay()

                    if (filesByMonth[monthDate] == null) {
                        filesByMonth[monthDate] = [it]
                    } else {
                        filesByMonth[monthDate] << it
                    }
                }
            }
        }

        filesByMonth.each {
            def newFolderName = outputFormat.format(Date.from((it.key as LocalDateTime).atZone(ZoneId.systemDefault()).toInstant()))
            def folder = new File(folderPath + File.separator + newFolderName)

            if (folder.mkdir()) {
                println("Folder ${folder.getName()} was created...")
            }

            it.value.each { file ->
                File fileToMove = file as File
                Path fileAfterMove = folder.toPath().resolve(fileToMove.getName())

                Files.move(fileToMove.toPath(), fileAfterMove, StandardCopyOption.REPLACE_EXISTING)

                println("File ${fileAfterMove} was moved successfully...")

                result << newFolderName
            }
        }

        result.size() > 0 ?: println("No files were moved by script")

        result
    }

    private static YearsAndMonths getYearsAndMonths(LocalDateTime startDate, LocalDateTime endDate) {

        def yearsAndMonths = new YearsAndMonths()
        def tempDate = startDate

        def years = startDate.until(endDate, ChronoUnit.YEARS)
        yearsAndMonths.years += years

        tempDate = tempDate.plusYears(years)

        def months = tempDate.until(endDate, ChronoUnit.MONTHS)
        yearsAndMonths.months = months.toInteger()

        yearsAndMonths
    }

    private static GString getFormattedYearsAndMonthsString(LocalDateTime startDate, LocalDateTime endDate) {

        def yearsAndMonths = getYearsAndMonths(startDate, endDate)
        def yearString = getFormattedDateString(ChronoUnit.YEARS, yearsAndMonths.years)
        def monthString = getFormattedDateString(ChronoUnit.MONTHS, yearsAndMonths.months)

        (yearsAndMonths.years == 0 ? "${monthString}" : "${yearString} ${monthString}")
    }

    private static String getFormattedDateString(ChronoUnit type, int unit) {
        GString result = "${unit}"

        def appendMonthSuffix = {
            if (unit == 0 || (5 <= unit && unit <= 12)) {
                result += " месяцев"
            } else if (2 <= unit && unit <= 4) {
                result += " месяца"
            } else {
                result += " месяц"
            }
        }

        def appendYearSuffix = {
            def rem100 = unit % 100
            def rem = unit % 10

            if (11 <= rem100 && rem100 <= 20) {
                result += " лет"
            } else {
                if (2 <= rem && rem <= 4) {
                    result += " года"
                } else if (rem == 0 || (5 <= rem && rem <= 10)) {
                    result += " лет"
                } else {
                    result += " год"
                }
            }
        }

        if (ChronoUnit.MONTHS == type) {
            appendMonthSuffix()
        } else if (ChronoUnit.YEARS == type) {
            appendYearSuffix()
        } else {
            throw new UnsupportedOperationException("Not implemented!")
        }
    }

    static void main(String[] args) {

        Map<String, String> paramsMap = [:]

        args[0].split(",").each { str ->
            String[] splittedKeyValue = str.trim().split("=")
            paramsMap[splittedKeyValue[0]] = splittedKeyValue[1]
        }

        def folderPath = paramsMap["folderPath"]
        def fileExt = paramsMap["ext"]
        def startDate = paramsMap["startDate"]
        def scriptType = paramsMap["scriptType"]

        startDate != null ? println("folder to apply script - ${folderPath}, extension of files - ${fileExt}, start date - ${startDate}, " +
                "script type - ${scriptType}") : println("folder to apply script - ${folderPath}, extension of files - ${fileExt}, script type - ${scriptType}")

        def result

        switch (scriptType) {
            case "byMonth":
                result = applyFilesByMonthsScript(folderPath, fileExt)
                break
            case "byPeriod":
                result = applyFilesByPeriodScript(folderPath, fileExt, startDate)
                break
            default:
                throw new IllegalArgumentException("Unknown script type ${scriptType}")
        }

        result.size() == 0 ?: println("Files were moved to ${result} folders...]")
    }
}
