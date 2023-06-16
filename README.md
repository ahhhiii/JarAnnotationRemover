# JarAnnotationRemover

Simply removes annotations you want removed from methods, classes, and fields.
Particularly useful for obfuscators that use annotations to mark what transformers to use such as Stringer (I actually made this for JnicHelper).

## Usage

1. Clone repo
2. Replace **annotationsToRemove** with your annotations.
3. If required, add your whitelisted packages to remove annotations from. If you want the whole jar to be cleansed, just use `""`.
5. Set your input and output **path**.
6. Go

### Example

```java
public class Launcher {

    public static void main(String[] args) {
        List<String> annotationsToRemove = Arrays.asList(
                "net.bruhitsalex.jnicinterface.JNIC",
                "net.bruhitsalex.jnicinterface.Performance",
                "kotlin.Metadata"
        );

        File input = new File("jars/test.jar");
        File output = new File("jars/test-output.jar");

        Processor processor = new Processor(input, output, annotationsToRemove, "net.bruhitsalex.testprogram");
        processor.start();
    }

}
```

#### Before

![image-20220402021758623](https://i.gyazo.com/b0ba46bec12f77823227a16a076ab939.png)

#### After

![image-20220402021717918](https://i.gyazo.com/6cb94f46e3bab4c3c322b01f1f3692d1.png)
