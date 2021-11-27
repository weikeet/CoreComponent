# Excellent library list

## javakam/FileOperator

https://github.com/javakam/FileOperator

涵盖了Android系统文件的创建/删除/复制/打开文件(目录)、获取文件(目录)大小、获取常用目录、获取文件名称及后缀、获取MimeType以及MediaStore和SAF的相关操作等常用功能，
并且也处理了获取文件Uri/Path的兼容问题、图片压缩和文件选择等功能。

```groovy
repositories {
    mavenCentral()
}

implementation 'com.github.javakam:file.core:2.3.0@aar'      //核心库必选(Core library required)
implementation 'com.github.javakam:file.selector:2.3.0@aar'  //文件选择器(File selector)
implementation 'com.github.javakam:file.compressor:2.3.0@aar'//图片压缩, 修改自Luban(Image compression, based on Luban)
```
