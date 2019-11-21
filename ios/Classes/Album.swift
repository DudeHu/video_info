//
//  Album.swift
//  file_picker
//
//  Created by Kasem Mohamed on 6/29/19.
//
import Foundation

struct Album: Codable {
    var id: String
    var title: String = ""
    var files: [MediaFile]
    
    init(id: String, name: String, files: [MediaFile]) {
        self.id = id
        self.title = name
        self.files = files
    }
}
