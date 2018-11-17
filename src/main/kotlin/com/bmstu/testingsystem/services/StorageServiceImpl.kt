package com.bmstu.testingsystem.services


import java.io.IOException
import java.io.InputStream
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream

import com.bmstu.testingsystem.config.StorageProperties
import liquibase.util.file.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class StorageServiceImpl @Autowired
constructor(val properties: StorageProperties) : StorageService {

    private val rootLocation: Path


    init {
        this.rootLocation = Paths.get(properties.location)
    }

    override fun store(file: MultipartFile) {
        val filename = StringUtils.cleanPath(file.originalFilename!!)
        storeAs(file, filename)
    }


    override fun storeAs(file: MultipartFile, filename: String): String {
        val ext = FilenameUtils.getExtension(file.originalFilename)
        try {
            if (file.isEmpty) {
                throw IllegalStateException("Failed to store empty file $filename")
            }
            if (filename.contains("..")) {
                throw IllegalStateException(
                        "Cannot store file with relative path outside current directory $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, this.rootLocation.resolve("$filename.$ext"),
                        StandardCopyOption.REPLACE_EXISTING)
            }
            return "$filename.$ext"
        } catch (e: IOException) {
            throw IllegalStateException("Failed to store file $filename", e)
        }

    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter { path -> path != this.rootLocation }
                    .map{ this.rootLocation.relativize(it) }
        } catch (e: IOException) {
            throw IllegalStateException("Failed to read stored files", e)
        }

    }

    override fun load(filename: String): Path {
        return rootLocation.resolve(filename)
    }

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            return if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw IllegalStateException(
                        "Could not read file: $filename")

            }
        } catch (e: MalformedURLException) {
            throw IllegalStateException("Could not read file: $filename", e)
        }

    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        File(properties.location).mkdirs()
    }
}