def loadScript(String path) {
    GroovyShell shell = new GroovyShell()
    return shell.parse(new File(path))
}
def util = loadScript('Util.groovy')

String PROJECT_PATH = util.readStringByTerminal(
        '===Please input the Absolute Path Of the Project===')
        //'/Users/2bab/Desktop/Binit/Demo'
String MODULE_NAME = "/" + util.readStringByTerminal(
        '===Please input the Name of Major Module===') // app
int FLAG = util.readIntByTerminal('Which method do you need?\nAll ... 0\nUpdate Common ... 1')

//=========================== Get PackageName =============================
String MANIFEST_PATH = '/src/main/AndroidManifest.xml'
File file = new File(PROJECT_PATH + MODULE_NAME + MANIFEST_PATH)
if(!file.exists()) {
	println '***Error: AndroidManifest.xml is Not Found***'
	return
}
String manifest = util.readFileByLines(file)
String packageRex = "(?<=package=\")[a-z0-9A-Z_\\-.]+(?=\")"
String PACKAGE = util.findString(manifest, packageRex, 'packageName')
println "find packageName : ${PACKAGE}"


//=========================== Copy Files =============================
util.copyFile('bingyan-common', PROJECT_PATH + '/bingyan-common')
util.appendTextAtTheEndOfFile('\r\ninclude \':bingyan-common\'', PROJECT_PATH + '/settings.gradle')
if (FLAG == 1) return
util.copyFile('script/.gitignore', PROJECT_PATH + '/.gitignore')
util.copyFile('script/keystore.gradle', PROJECT_PATH + MODULE_NAME + '/keystore.gradle')
util.copyFile('script/build.gradle', PROJECT_PATH + MODULE_NAME + '/build.gradle')
util.replaceText(PACKAGE, 'PACKAGE_NAME', PROJECT_PATH + MODULE_NAME + '/build.gradle')