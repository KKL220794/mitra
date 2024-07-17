import React, { useEffect, useCallback, useState, useLayoutEffect } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { Avatar } from 'react-native-elements';
// import { auth, db } from '../firebase';
// import { signOut } from 'firebase/auth';
import { GiftedChat } from 'react-native-gifted-chat';

const Chat = ({ navigation }) => {
    const [messages, setMessages] = useState([]);
    const signOutNow = () => {
        console.log('signed out');
        // signOut(auth).then(() => {
        //     // Sign-out successful.
        //     navigation.replace('Login');
        // }).catch((error) => {
        //     // An error happened.
        // });
    }
    useLayoutEffect(() => {
        navigation.setOptions({
            headerLeft: () => (
                <View style={{ marginLeft: 20 }}>
                    <Avatar
                        rounded
                        source={{
                            // uri: auth?.currentUser?.photoURL,
                            uri: 'https://picsum.photos/140',
                        }}
                    />
                </View>
            ),
            headerRight: () => (
                <TouchableOpacity style={{
                    marginRight: 10
                }}
                    onPress={signOutNow}
                >
                    <Text>logout</Text>
                </TouchableOpacity>
            )
        })
    }, [navigation]);

    useEffect(() => {
        setMessages([
            {
                _id: 1,
                text: 'Hello developer',
                createdAt: new Date(),
                user: {
                    _id: 2,
                    name: 'React Native',
                    avatar: 'https://picsum.photos/140',
                },
            },
        ])
    }, []);
    const onSend = useCallback((messages = []) => {
        setMessages(previousMessages => GiftedChat.append(previousMessages, messages))
    }, []);
    return (
        <GiftedChat
            messages={messages}
            showAvatarForEveryMessage={true}
            onSend={messages => onSend(messages)}
            user={{
                _id: 'test@mail.com',
                name: 'John Doe',
                avatar: 'https://picsum.photos/140'
                // _id: auth?.currentUser?.email,
                // name: auth?.currentUser?.displayName,
                // avatar: auth?.currentUser?.photoURL
            }}
        />
    );
}

export default Chat;