# JarAnnotationRemover

Simply removes annotations you want removed from methods, classes, and fields.
Particularly useful for obfuscators that use annotations to mark what transformers to use such as Stringer (I actually made this for JnicHelper).

## Usage

1. Clone repo
2. Add your **libraries** that include the annotations you want to remove as dependencies.
3. Replace **annotationsToRemove** with your annotations.
4. Set your input and output **path**.
5. Go

### Example

```java
public class Launcher {

    public static void main(String[] args) {
        List<Class> annotationsToRemove = Arrays.asList(
                ExtraObfuscation.class,
                JNIC.class,
                HideAccess.class,
                StringEncryption.class
        );

        File input = new File("jars/test.jar");
        File output = new File("jars/test-output.jar");

        Processor processor = new Processor(input, output, annotationsToRemove);
        processor.start();
    }

}
```

![image-20220402021119573](https://i.gyazo.com/72104c669992e0c33378c7b613377c9f.png)

#### Before

![image-20220402021758623](https://i.gyazo.com/b0ba46bec12f77823227a16a076ab939.png)

#### After

![image-20220402021717918](https://i.gyazo.com/6cb94f46e3bab4c3c322b01f1f3692d1.png)
