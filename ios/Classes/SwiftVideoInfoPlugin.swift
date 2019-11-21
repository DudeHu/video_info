import Flutter
import UIKit
import Photos

public class SwiftVideoInfoPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "moon/video_info", binaryMessenger: registrar.messenger())
    let instance = SwiftVideoInfoPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
       case "getVideoList":
            DispatchQueue(label: "getVideoList").async {
                let albums = FileFetcher.getVideoList()
                let encodedData = try? JSONEncoder().encode(albums)
                let json = String(data: encodedData!, encoding: .utf8)!
                result(json)
            }
       default:
            result(FlutterMethodNotImplemented)
    }
  }
}
