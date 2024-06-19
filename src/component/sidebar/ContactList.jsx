import COLORS from '../../constant/color'

const ContactList = ({ contactList }) => {
  return (
    <div className="flex flex-col mt-8 flex-1">
      <div className="flex flex-row items-center justify-between text-xs">
        <span className="font-bold">Active Friends</span>
        {/* <span
          className="flex items-center justify-center bg-gray-300 h-4 w-4 rounded-full"
        >4</span
        > */}
      </div>
      <div className="flex flex-col space-y-1 mt-4 -mx-2 h-48 overflow-y-auto">
        {contactList.map((contact, index) => (
          <button
            key={index}
            className="flex flex-row items-center hover:bg-gray-100 rounded-xl p-2"
          >
            <div
              className={`flex items-center justify-center h-8 w-8 bg-${COLORS[index%COLORS.length]}-200 rounded-full`}
            >
              {contact[0]}
            </div>
            <div className="ml-2 text-sm font-semibold">{contact}</div>
            {contact.unreadMessages > 0 && (
              <div
                className="flex items-center justify-center ml-auto text-xs text-white bg-red-500 h-4 w-4 rounded leading-none"
              >
                {contact.unreadMessages}
              </div>
            )}
          </button>
        ))}
      </div>
    </div>
  )
}

export default ContactList;