import java.nio.channels.FileChannel
import java.util.regex.Matcher
import java.util.regex.Pattern

def readFileByLines(File file) {
    BufferedReader reader = null
    StringBuilder builder = new StringBuilder()
    try {
        reader = new BufferedReader(new FileReader(file))
        String tempString
        while ((tempString = reader.readLine()) != null) {
            builder.append(tempString)
        }
        reader.close()
        return builder.toString()
    } catch (IOException e) {
        e.printStackTrace()
    } finally {
        if (reader != null) {
            try {
                reader.close()
            } catch (IOException e1) {
                e1.printStackTrace()
            }
        }
    }
}

def readStringByTerminal(String hint) {
    println hint
    Scanner sc = new Scanner(System.in)
    return sc.nextLine()
}

def readIntByTerminal(String hint) {
    println hint
    Scanner sc = new Scanner(System.in)
    return sc.nextInt()
}

def findString(String origin, String rexText, String hint) {
    String rex = rexText
    Pattern pattern = Pattern.compile(rex)
    Matcher matcher = pattern.matcher(origin)
    if (!matcher.find()) {
        println "***Error: ${hint} is Not Found***"
        return
    }
    return matcher.group()
}

def copyFile(File fromFile, File toFile) {
    long copySizes = 0;
    if (!fromFile.exists()) {
        println("${fromFile.absolutePath} is not exist")
        return -1
    }
    if (!toFile.exists()) {
        if (fromFile.isDirectory()) {
            toFile.mkdir()
        } else {
            toFile.createNewFile()
        }
    }
    if (fromFile.isDirectory()) {
        File[] childFiles = fromFile.listFiles()
        for (File f : childFiles) {
            copyFile(f.absolutePath, toFile.absolutePath + "/" + f.name)
        }
    } else {
        try {
            print("copy ${fromFile.name} ... ")
            FileChannel fcFrom = new FileInputStream(fromFile).getChannel()
            FileChannel fcTo = new FileOutputStream(toFile).getChannel()
            long size = fcFrom.size()
            fcFrom.transferTo(0, fcFrom.size(), fcTo)
            fcFrom.close()
            fcTo.close()
            copySizes = size
            println('done')
        } catch (FileNotFoundException e) {
            e.printStackTrace()
            println('failed')
        } catch (IOException e) {
            e.printStackTrace()
            println('failed')
        }
    }
    return copySizes
}

def copyFile(String from, String to) {
    File fromFile = new File(from)
    File toFile = new File(to)
    return copyFile(fromFile, toFile)
}

def replaceText(String content, String placeholder, String filePath) {

    try {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))
        StringBuffer sb = new StringBuffer()
        String str = null
        while ((str = br.readLine()) != null) {
            str = str.replace(placeholder, content)
            sb.append(str + "\r\n")
        }
        sb.setLength(sb.length() - 1)
        br.close()
        out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
        out.write(sb.toString())
        out.flush()
        out.close()
    } catch (IOException e) {
        e.printStackTrace()
    }
}

def appendTextAtTheEndOfFile(String content, String filePath) {
    try {
        FileWriter writer = new FileWriter(filePath, true)
        writer.write(content)
        writer.close()
    } catch (IOException e) {
        e.printStackTrace()
    }
}

//=========================== TestCase =============================
//copyFile("/Users/2bab/Desktop/Binit/src/common", "/Users/2bab/Desktop/Binit/src/commonTest")
