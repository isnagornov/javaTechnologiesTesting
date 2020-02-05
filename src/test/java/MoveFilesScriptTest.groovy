import org.junit.Test

class MoveFilesScriptTest {

    @Test
    void runScriptTest1() {
        MoveFilesScript.main("folderPath=d:/temp,ext=xml|txt|pdf|log,scriptType=byPeriod,startDate=01.09.2019")
    }

    @Test
    void runScriptTest2() {
        MoveFilesScript.main("folderPath=d:/temp,ext=xml|txt|pdf|log,scriptType=byMonth,startDate=01.09.2019")
    }
}
