# Create: Parallel Lines

NeoForge 模组开发工程（Minecraft 1.21.1）。

| 组件 | 版本 |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.265 |
| Java | 21（toolchain） |
| ModDevGradle | 2.0.91 |
| Gradle | 8.14.2（wrapper） |
| Parchment | 1.21.1 / 2024.11.17 |

## 环境要求

- JDK 21。工程声明了 Java 21 toolchain：Gradle 会在本机查找已安装的 JDK 21，
  找不到时通过 `foojay-resolver-convention` 自动下载。
- 首次构建需要联网，从 `maven.neoforged.net`、Maven Central 与 `piston-meta` 拉取依赖。

## 常用命令

```powershell
# 构建模组 jar（输出到 build/libs/）
.\gradlew.bat build

# 启动带模组的客户端（开发环境，离线账号）
.\gradlew.bat runClient

# 启动专用服务端
.\gradlew.bat runServer

# 运行 GameTest 服务端
.\gradlew.bat runGameTestServer

# 数据生成：输出到 src/generated/resources
.\gradlew.bat runData

# 生成 / 更新 IDE 工程文件
.\gradlew.bat idea
```

> 第一次 `runClient` 会额外下载 Minecraft 资源与运行库，耗时较长。

## 目录结构

```
build.gradle                     构建脚本（ModDevGradle 配置、run 配置、元数据展开）
gradle.properties                版本号与模组元数据（改 neo_version / mod_version 等）
settings.gradle                  插件仓库与 foojay toolchain 解析器
src/main/java/...                模组源码
src/main/resources/              资源（assets、data、META-INF/accesstransformer.cfg 等）
src/main/templates/              neoforge.mods.toml 模板，${...} 占位符由 Gradle 展开
src/generated/resources/         数据生成输出目录（已加入构建资源路径）
```

## 元数据

模组 ID、版本、作者等信息集中在 `gradle.properties`，构建时展开进
`src/main/templates/META-INF/neoforge.mods.toml`。修改 `mod_id` 时需要同步修改
`CreateParallelLines.MODID`。

## Create 依赖（开发 + 生产）

本工程已按官方文档 [Depending on Create — NeoForge 1.21.1](https://wiki.createmod.net/developers/depend-on-create/neoforge-1.21.1)
配置好对 **Create 6.0.10** 的依赖。

开发环境依赖（`build.gradle` + `gradle.properties`）：

| 依赖 | 坐标 |
| --- | --- |
| Create | `com.simibubi.create:create-1.21.1:6.0.10-280:slim`（`transitive = false`） |
| Ponder | `net.createmod.ponder:ponder-neoforge:1.0.82+mc1.21.1` |
| Flywheel | `dev.engine-room.flywheel:flywheel-neoforge-api-1.21.1:1.0.6`（compileOnly）<br>`dev.engine-room.flywheel:flywheel-neoforge-1.21.1:1.0.6`（runtimeOnly） |
| Registrate | `com.tterrag.registrate:Registrate:MC1.21-1.3.0+67` |

其中 `6.0.10-280` 是与 Create 6.0.10 发布 jar 对应的 CI 构建号；上游每次发版都会在
[wiki 页面](https://wiki.createmod.net/developers/depend-on-create/neoforge-1.21.1) 更新该构建号。

生产环境依赖（`src/main/templates/META-INF/neoforge.mods.toml`）：

```toml
[[dependencies.${mod_id}]]
    modId="create"
    type="required"
    versionRange="[6.0.10,6.1.0)"
    ordering="AFTER"
    side="BOTH"
```

缺少 Create 或版本低于 6.0.10 时，NeoForge 会在启动阶段直接报错，而不是等到运行中才崩溃。

## 添加其他依赖

在 `build.gradle` 的 `dependencies { }` 中声明：

```groovy
dependencies {
    compileOnly("...")     // 仅编译期 API，不打包
    runtimeOnly("...")     // 仅运行期
    localRuntime("...")    // 运行期存在，且不会传递给下游
}
```

新增第三方 Maven 仓库时，注意在 `repositories { }` 中声明；若仓库未声明，Gradle 会在解析阶段直接失败。

## 许可

MIT，见 [LICENSE](LICENSE)。
