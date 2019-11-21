import Foundation

struct MediaFile : Codable {
    var id: String
    var title: String?
    var dateAdded: Int? // seconds since 1970
    var path: String?
    var thumbPath: String?
    var orientation: Int
    var duration: Double?
    var mimeType: String?
    var type: MediaType
    
    init(id: String, title: String?, dateAdded: Int?, path: String?, thumbPath: String?, orientation: Int, duration: Double?, mimeType: String?, type: MediaType) {
        self.id = id
        self.dateAdded = dateAdded
        self.path = path
        self.thumbPath = thumbPath
        self.orientation = orientation
        self.duration = duration
        self.mimeType = mimeType
        self.type = type
        self.title = title
    }
}

enum MediaType: Int, Codable {
    case IMAGE
    case VIDEO
}
