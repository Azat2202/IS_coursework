import {useCreateCharacterMutation, useGenerateFactQuery} from "../../store/types.generated";
import {useState} from "react";
import toast from "react-hot-toast";
import {useParams} from "react-router-dom";

interface characteristicChooseProps {
    options: string[] | undefined,
    selectedId: number,
    onSelect: (id: number) => void,
}

function CharacteristicsChoose({options, selectedId, onSelect}: characteristicChooseProps) {
    return (
        <div className="flex flex-col space-y-2 bg-burgundy-700 p-4 rounded-lg shadow-md w-full">
            {options?.map((option, index) => (
                <div
                    key={index}
                    className={`p-3 rounded-lg border-spacing-1 border-burgundy-900 cursor-pointer text-center transition duration-300 ${
                        index === selectedId
                            ? "bg-burgundy-900 text-white"
                            : "bg-burgundy-800 hover:bg-burgundy-850 text-gray-300"
                    }`}
                    onClick={() => onSelect(index)}
                >
                    {option}
                </div>
            ))}
        </div>
    );
}

export function SelectCharacteristics() {
    const { roomId: roomIdStr } = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: characteristics} = useGenerateFactQuery({roomId: roomId})
    const [saveCharacteristics] = useCreateCharacterMutation()
    const [name, setName] = useState("")
    const [bodyTypeId, setBodyTypeId] = useState(1)
    const [healthId, setHealthId] = useState(1)
    const [traitId, setTraitId] = useState(1)
    const [hobbyId, setHobbyId] = useState(1)
    const [professionId, setProfessionId] = useState(1)
    const [phobiaId, setPhobiaId] = useState(1)
    const [equipmentId, setEquipmentId] = useState(1)
    const [bagId, setBagId] = useState(1)

    const characteristicsList = [
        {value: characteristics?.bodyTypes, selectedId: bodyTypeId, setter: setBodyTypeId},
        {value: characteristics?.healths, selectedId: healthId, setter: setHealthId},
        {value: characteristics?.traits, selectedId: traitId, setter: setTraitId},
        {value: characteristics?.hobbies, selectedId: hobbyId, setter: setHobbyId},
        {value: characteristics?.professions, selectedId: professionId, setter: setProfessionId},
        {value: characteristics?.phobiases, selectedId: phobiaId, setter: setPhobiaId},
        {value: characteristics?.equipments, selectedId: equipmentId, setter: setEquipmentId},
        {value: characteristics?.bags, selectedId: bagId, setter: setBagId}
    ].map(({value, selectedId, setter}) => {
        return {
            value: value ?? [],
            selectedId: selectedId,
            setter: setter
        }
    })

    function validateCharacteristics() {
        const levelSum = characteristicsList
            .map(({value, selectedId}) => value.at(selectedId)?.level ?? 0)
            .reduce((a, b) => a + b, 0)
        return levelSum !== 0
    }

    async function selectCharacteristics() {
        await saveCharacteristics({
            createCharacterRequest: {
                    name: name,
                    age: 25,
                    sex: "MALE",
                    notes: "",
                    isActive: true,
                    bodyTypeId: bodyTypeId,
                    healthId: healthId,
                    traitId: traitId,
                    hobbyId: hobbyId,
                    professionId: professionId,
                    phobiaId: phobiaId,
                    equipmentId: equipmentId,
                    bagId: bagId,
                    roomId: roomId
            }}).unwrap()
            .then(() => toast.success("Персонаж сохранен!"))
            .catch(e => toast.error("Создать персонажа не удалось"))
    }

    return (
        <div className="flex flex-col items-center space-y-6">
            <input
                type="text"
                value={name}
                onChange={e => setName(e.target.value)}
                className="w-full max-w-md p-3 placeholder:text-burgundy-400 text-burgundy-950 bg-burgundy-200 rounded-lg border-2 border-burgundy-700 focus:outline-none focus:ring-2 focus:ring-burgundy-400"
                placeholder="Введите имя персонажа"
            />

            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 w-full max-w-5xl">
                {characteristicsList.map((characteristic, index) => (
                    <CharacteristicsChoose
                        key={index}
                        options={characteristic.value.map(v => v.name ?? "СЕКРЕТ")}
                        selectedId={characteristic.selectedId}
                        onSelect={characteristic.setter}
                    />
                ))}
            </div>

            <button
                onClick={selectCharacteristics}
                className="py-3 px-6 bg-green-700 hover:bg-green-800 disabled:opacity-50 disabled:bg-red-500 text-white font-semibold text-lg rounded-lg transition duration-300 transform hover:scale-105"
                disabled={validateCharacteristics()}
            >
                Выбрать характеристики
            </button>
        </div>
    );
}