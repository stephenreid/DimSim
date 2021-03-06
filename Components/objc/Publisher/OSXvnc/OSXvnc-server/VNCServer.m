//
//  VNCServer.m
//  OSXvnc
//
//  Created by Jonathan Gillaspie on Mon Nov 17 2003.
//  Copyright (c) 2003 Redstone Software, Inc. All rights reserved.
//

#import "VNCServer.h"

#import "rfb.h"

#include <unistd.h>

@implementation VNCServer

- (void) userSwitched: (NSNotification *) aNotification {
    NSLog(@"User Switched Restarting - %@", [aNotification name]);

    sleep(10);
	[msgClient messageToServer:@"{screencastResult:\"2\"}" from:@"dsc" to:@"wkplugin"];
	[msgClient messageToServer:@"idle" from:@"dsc" to:@"Screencaster"];
    rfbShutdown();

    exit(2);
}

- (void) connectHost: (NSNotification *) aNotification {
	NSAutoreleasePool *pool=[[NSAutoreleasePool alloc] init];
	
//	char *reverseHost = [[[aNotification userInfo] objectForKey:@"ConnectHost"] cStringUsingEncoding:NSASCIIStringEncoding];
	char *reverseHost = (char *)[[[aNotification userInfo] objectForKey:@"ConnectHost"] cString];
	int reversePort = [[[aNotification userInfo] objectForKey:@"ConnectPort"] intValue];
	
    NSLog(@"Connecting VNC Client %s(%d)",reverseHost,reversePort);
	connectReverseClient(reverseHost,reversePort);

	[pool release];	
}

@end
