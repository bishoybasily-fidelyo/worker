# Local messaging for android

[![](https://jitpack.io/v/bishoybasily-fidelyo/messenger.svg)](https://jitpack.io/#bishoybasily-fidelyo/messenger)

## Overview

This library is about basic implementation for the rabbit-mq topic exchange,
the exchange you can filter it's messages by routing key.

## Eample android kotlin

**Full example**
``` kotlin
import com.fidelyo.messenger.Exchange
import com.fidelyo.messenger.Subscriber

 //...

    val exchange = Exchange()
    
 //...
 
        val subscriber1 = Subscriber<String>("routing_key2", Handler(Looper.myLooper()))
        subscriber1.callback = object : Subscriber.Callback<String> {

            override fun onMessage(message: String) {
                Log.i(TAG, message + " 1")
            }

        }
        exchange.register(subscriber1)

        val subscriber2 = Subscriber<String>("routing_key2", Handler(Looper.myLooper()))
        subscriber2.callback = object : Subscriber.Callback<String> {

            override fun onMessage(message: String) {
                Log.i(TAG, message + " 2")
            }

        }
        exchange.register(subscriber2)

        exchange.publish("Hello", "routing_key2")
        
 // ...
 
```

Moreover, you can extend the exchange and override "shouldIPublish" method to write your own routing decision