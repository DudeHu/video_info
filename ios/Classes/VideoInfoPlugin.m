#import "VideoInfoPlugin.h"
#import <video_info/video_info-Swift.h>

@implementation VideoInfoPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftVideoInfoPlugin registerWithRegistrar:registrar];
}
@end
