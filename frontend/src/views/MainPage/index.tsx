import {FormEvent, useState} from "react";
import toast from "react-hot-toast";
import {useCreateRoomMutation, useJoinRoomMutation} from "../../store/types.generated";
import {useNavigate} from "react-router-dom";

export function MainPage() {
    const [code, setCode] = useState("")
    const [createRoom] = useCreateRoomMutation()
    const [joinRoom] = useJoinRoomMutation()
    const navigate = useNavigate()

    async function createGame() {
        createRoom().unwrap()
            .then(response => navigate(`/room/${response.id}`))
            .catch(() => toast.error("Создать комнату не удалось..."))
    }

    async function enterGame(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (code.trim().length === 0) {
            toast.error("Заполните код комнаты!")
            return
        }
        await joinRoom({joinCode: code}).unwrap()
            .then(response => navigate(`/room/${response.id}`))
            .catch(() => toast.error("Такой комнаты не существует либо игра уже началась!"))
    }

    return (
        <div>
            <h1>Главная страница</h1>
            <p>У вас есть всего две возможности: создать игру и подключиться к уже существующей игре</p>
            <button type={"button"}
                    onClick={createGame}
            >СОЗДАТЬ ИГРУ
            </button>
            <form onSubmit={enterGame}>
                <label>
                    Введите код комнаты:
                </label>
                <input type={"text"}
                       onChange={e => setCode(e.target.value)}
                       placeholder={"Код комнаты"}
                />
                <button type={"submit"}>ВОЙТИ В ИГРУ ПО КОДУ</button>
            </form>
        </div>
    )
}